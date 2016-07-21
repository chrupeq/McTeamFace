package com.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.EventCauseFailuresDAO;
import com.ait.db.model.EventCauseFailures;
import com.ait.db.model.EventCauseFailuresCounter;
import com.ait.db.model.Event_cause;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
public class EventCauseFailuresDAOTest {
	private List<Event_cause> eventCauseList;
	private List<EventCauseFailures> eventCauseFailuresList;
	private EventCauseFailuresCounter eventCauseFailuresCounter;
	private static final int TAC_NUMBER = 33000153;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Event_cause.class.getPackage())
				.addPackage(EventCauseFailuresDAO.class.getPackage())
				.addClass(IMSIStats.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@EJB
	EventCauseFailuresDAO eventCauseFailuresDAO;
	
	@Test
	public void getAllEventCauseCodesPerPhoneShouldReturnEventCauseObjects(){
		eventCauseList = eventCauseFailuresDAO.getAllEventCauseCodesPerPhone(TAC_NUMBER);
		assertNotNull(eventCauseList);
		assertFalse(eventCauseList.isEmpty());
		assertTrue(eventCauseList.get(0) instanceof Event_cause);
	}
	@Test
	public void getAllEventCauseCodesPerPhoneShouldReturnTheRightNumberOfEventCauseObjects(){
		eventCauseList = eventCauseFailuresDAO.getAllEventCauseCodesPerPhone(TAC_NUMBER);
		assertEquals(3, eventCauseList.size());
	}
	@Test
	public void getAllEventCauseCodesPerPhoneShouldReturnAnEmptyListIfNoTacCodeFound(){
		int tacNumber = 100000;
		eventCauseList = eventCauseFailuresDAO.getAllEventCauseCodesPerPhone(tacNumber);
		assertNotNull(eventCauseList);
		assertTrue(eventCauseList.isEmpty());
	}
	@Test
	public void getEventCauseFailuresShouldReturnAListOfEventCauseFailuresObjects(){
		eventCauseList = eventCauseFailuresDAO.getAllEventCauseCodesPerPhone(TAC_NUMBER);
		eventCauseFailuresCounter = new EventCauseFailuresCounter(eventCauseList);
		eventCauseFailuresList = eventCauseFailuresCounter.getEventCauseFailures();
		assertNotNull(eventCauseFailuresList);
		assertFalse(eventCauseFailuresList.isEmpty());
	}
	@Test
	public void getEventCauseFailuresShouldReturnCorrectResults(){
		eventCauseList = eventCauseFailuresDAO.getAllEventCauseCodesPerPhone(TAC_NUMBER);
		eventCauseFailuresCounter = new EventCauseFailuresCounter(eventCauseList);
		eventCauseFailuresList = eventCauseFailuresCounter.getEventCauseFailures();
		assertEquals(3, eventCauseFailuresList.size());
		assertEquals(new Integer(1), eventCauseFailuresList.get(0).getNumberOfFailures());
		assertEquals(new Integer(1), eventCauseFailuresList.get(1).getNumberOfFailures());
		assertEquals(new Integer(1), eventCauseFailuresList.get(2).getNumberOfFailures());
	}
	@Test
	public void getEventCauseFailuresShouldReturnResultsSortedByNumberOfFailuresInADescendingOrder() {
		int tacNumber = 33000253;
		eventCauseList = eventCauseFailuresDAO.getAllEventCauseCodesPerPhone(tacNumber);
		eventCauseFailuresCounter = new EventCauseFailuresCounter(eventCauseList);
		eventCauseFailuresList = eventCauseFailuresCounter.getEventCauseFailures();
		assertEquals(2, eventCauseFailuresList.size());
		assertEquals(new Integer(2), eventCauseFailuresList.get(0).getNumberOfFailures());
		assertEquals(new Integer(1), eventCauseFailuresList.get(1).getNumberOfFailures());
	}	
}
