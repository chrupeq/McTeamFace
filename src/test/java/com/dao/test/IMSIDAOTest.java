package com.dao.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.IMSIDAO;
import com.ait.db.model.Base_data;
import com.ait.gui.User;

@RunWith(Arquillian.class)
public class IMSIDAOTest {
	String dateOne;
	String dateTwo;
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addPackage(IMSIDAO.class.getPackage())
				.addPackage(User.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@EJB
	IMSIDAO imsiDAO;
	
	@Before
	public void setUp() {
		dateOne = "2013-01-11 17:15:00";
		dateTwo = "2013-01-11 17:16:00";
	}
	@Test
	public void shouldReturnBaseDataByDate() throws ParseException {
		
		List<Base_data> baseDataList = imsiDAO.getIMSIsByDates(dateOne, dateTwo);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(7, baseDataList.size());
	}
	@Test
	public void shouldReturnAPArsedCalendarObject() throws ParseException {
		Calendar[] datesAsCalendarObjects = imsiDAO.parseStringIntoCalendarObject(dateOne, dateTwo);
		assertNotNull(datesAsCalendarObjects);
		assertEquals(2, datesAsCalendarObjects.length);
	}

}
