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
import com.ait.db.model.IMSI;

@RunWith(Arquillian.class)
public class IMSIDAOTest {
	private String dateOne;
	private String dateTwo;
	private String dateThree;
	private String dateFour;
	private String dateFive;
	private List<Base_data> baseDataList;
	private List<IMSI> IMSIList;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addPackage(IMSIDAO.class.getPackage())
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
		dateThree = "2013-01-11 17:17:00";
		dateFour = "2013-01-11 17:00:00";
		dateFive = "2013-01-11 17:30:00";
//		dateSix = "2016-06-28 00:00:00";
		
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartOne() throws ParseException {
		baseDataList = imsiDAO.getIMSIsByDates(dateOne, dateTwo);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(7, baseDataList.size());
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartTwo() throws ParseException {
		baseDataList = imsiDAO.getIMSIsByDates(dateOne, dateThree);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(10, baseDataList.size());
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartThree() throws ParseException {
		baseDataList = imsiDAO.getIMSIsByDates(dateTwo, dateThree);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(6, baseDataList.size());
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartFour() throws ParseException {
		baseDataList = imsiDAO.getIMSIsByDates(dateFour, dateFive);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(10, baseDataList.size());
	}
	
	@Test
	public void shouldReturnAPArsedCalendarObject() throws ParseException {
		Calendar[] datesAsCalendarObjects = imsiDAO.parseStringIntoCalendarObject(dateOne, dateTwo);
		assertNotNull(datesAsCalendarObjects);
		assertEquals(2, datesAsCalendarObjects.length);
	}
	
	@Test
	public void shouldReturnDistinctIMSIs() {
		IMSIList = imsiDAO.getAllUniqueIMSIs();
		assertNotNull(IMSIList);
		assertFalse(IMSIList.isEmpty());
		assertEquals(3, IMSIList.size());
	}
	@Test
	public void shouldReturnCorrectIMSIs() {
		IMSIList = imsiDAO.getAllUniqueIMSIs();
//		assertNotNull()
	}
	

}
