package com.dao.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import static org.hamcrest.CoreMatchers.*;
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
import com.ait.db.model.IMSIWithValidFailureClasses;

@RunWith(Arquillian.class)
public class IMSIDAOTest {
	private String dateOne;
	private String dateTwo;
	private String dateThree;
	private String dateFour;
	private String dateFive;
	private String dateSix;
	private String dateSeven;
	private String invalidDateOne;
	private String invalidDateTwo;
	private List<BigInteger> IMSIList;
	private List<IMSIWithValidFailureClasses> imsiWithValidFailureClassses;
	
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
		dateSix = "2016-06-28 00:00:00";
		dateSeven = "2016-06-28 23:59:00";
		invalidDateOne = "29/06/2016 00:00";
		invalidDateTwo = "29/06/2016 00:01";
		
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartOne() throws ParseException {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateOne, dateTwo);
		assertNotNull(imsiWithValidFailureClassses);
		assertFalse(imsiWithValidFailureClassses.isEmpty());
		assertEquals(6, imsiWithValidFailureClassses.size());
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartTwo() throws ParseException {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateOne, dateThree);
		assertNotNull(imsiWithValidFailureClassses);
		assertFalse(imsiWithValidFailureClassses.isEmpty());
		assertEquals(9, imsiWithValidFailureClassses.size());
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartThree() throws ParseException {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateTwo, dateThree);
		assertNotNull(imsiWithValidFailureClassses);
		assertFalse(imsiWithValidFailureClassses.isEmpty());
		assertEquals(6, imsiWithValidFailureClassses.size());
	}
	@Test
	public void shouldReturnBaseDataBetweenTwoDatesPartFour() throws ParseException {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateFour, dateFive);
		assertNotNull(imsiWithValidFailureClassses);
		assertFalse(imsiWithValidFailureClassses.isEmpty());
		assertEquals(9, imsiWithValidFailureClassses.size());
	}
	@Test
	public void shouldReturnAnEmptyListIfDatesOutOfRange() throws ParseException {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateSix, dateSeven);
		assertNotNull(imsiWithValidFailureClassses);
		assertTrue(imsiWithValidFailureClassses.isEmpty());
	}
	@Test
	public void shouldREturnDateAsAFormattedString() throws ParseException {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateTwo, dateThree);
		String dateTime = "2013-11-01 17:16:00";
		assertEquals(dateTime, imsiWithValidFailureClassses.get(0).getDate_time());
	}
	@Test
	public void shouldReturnAParsedCalendarObject() throws ParseException {
		Calendar[] datesAsCalendarObjects = imsiDAO.parseStringIntoCalendarObject(dateOne, dateTwo);
		assertNotNull(datesAsCalendarObjects);
		assertEquals(2, datesAsCalendarObjects.length);
	}
	@Test
	public void shouldNotParseAnInvalidFormat() throws ParseException {
		Calendar[] datesAsCalendarObjects = imsiDAO.parseStringIntoCalendarObject(invalidDateOne, invalidDateTwo);
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
		BigInteger imsi = new BigInteger("344930000000011");
		assertEquals(imsi, IMSIList.get(0));
		
		imsi = new BigInteger("240210000000013");
		assertEquals(imsi, IMSIList.get(1));
		
		imsi = new BigInteger("310560000000012");
		assertEquals(imsi, IMSIList.get(2));	
	}
}
