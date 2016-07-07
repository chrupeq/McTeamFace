package com.dao.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
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

import com.ait.db.data.CauseCodeDAO;
import com.ait.db.data.EventCauseFailuresDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;

@RunWith(Arquillian.class)
public class CauseCodeDAOTest {
	
	private List<BigInteger> affectedIMSIs;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addPackage(CauseCodeDAO.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@EJB
	CauseCodeDAO causeCodeDAO;
	
	@Test
	public void getUniqueCauseCodesShouldReturnAListOfCauseCodes(){
		List<Integer> causeCodes = causeCodeDAO.getUniqueCauseCodes();
		assertNotNull(causeCodes);
		assertFalse(causeCodes.isEmpty());
		assertEquals(5, causeCodes.size());
	}
	@Test
	public void getUniqueCauseCodesShouldReturnAnOrderedListOfCauseCodes(){
		List<Integer> causeCodes = causeCodeDAO.getUniqueCauseCodes();
		assertEquals(0, causeCodes.get(0).intValue());
		assertEquals(11, causeCodes.get(1).intValue());
		assertEquals(12, causeCodes.get(2).intValue());
		assertEquals(13, causeCodes.get(3).intValue());
		assertEquals(23, causeCodes.get(4).intValue());
	}
	@Test
	public void getAffectedIMSIsShouldReturnAListOfIMSIs(){
		affectedIMSIs = causeCodeDAO.getAffectedIMSIs(13);
		assertNotNull(affectedIMSIs);
		assertFalse(affectedIMSIs.isEmpty());
		assertEquals(1, affectedIMSIs.size());
	}
	@Test
	public void getAffectedIMSIsShouldReturnUniqueIMSIs(){
		affectedIMSIs = causeCodeDAO.getAffectedIMSIs(11);
		assertEquals(3, affectedIMSIs.size());
	}
	@Test
	public void getAffectedIMSIsShouldReturnSortedIMSIs(){
		affectedIMSIs = causeCodeDAO.getAffectedIMSIs(11);
		assertEquals(new BigInteger("240210000000013"), affectedIMSIs.get(0));
		assertEquals(new BigInteger("310560000000012"), affectedIMSIs.get(1));
		assertEquals(new BigInteger("344930000000011"), affectedIMSIs.get(2));
	}
}
