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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.ait.db.data.IMSIDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.Failure_class;
import com.ait.db.model.IMSIWithEventIDAndCauseCode;
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
	private List<BigInteger> IMSIList;
	private List<IMSIWithValidFailureClasses> imsiWithValidFailureClassses;
	private List<IMSIWithEventIDAndCauseCode> imsisWithEventIDsAndCauseCodes;
	private List<Base_data> baseDataList;
	private List<Failure_class> failureClassList;
	
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
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	@Before
	public void setUp() {
		dateOne = "2013-01-11 17:15:00";
		dateTwo = "2013-01-11 17:16:00";
		dateThree = "2013-01-11 17:17:00";
		dateFour = "2013-01-11 17:00:00";
		dateFive = "2013-01-11 17:30:00";
		dateSix = "2016-06-28 00:00:00";
		dateSeven = "2016-06-28 23:59:00";
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
	public void shouldReturnDateAsAFormattedString() throws ParseException {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateTwo, dateThree);
		String dateTime = "11/01/2013 17:17:00";
		assertEquals(dateTime, imsiWithValidFailureClassses.get(0).getDate_time());
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
	@Test
	public void getIMSIsByDateShouldReturnOrderedIMSIs() {
		imsiWithValidFailureClassses = imsiDAO.getIMSIsByDates(dateFour, dateFive);
		
		BigInteger imsi = new BigInteger("240210000000013");
		// the first element
		assertEquals(imsiWithValidFailureClassses.get(0).getImsi(), imsi);
		
		imsi = new BigInteger("310560000000012");
		// element in the middle
		assertEquals(imsiWithValidFailureClassses.get(imsiWithValidFailureClassses.size()/2).getImsi(), imsi);
		
		imsi = new BigInteger("344930000000011");
		// last element in the list
		assertEquals(imsiWithValidFailureClassses.get(imsiWithValidFailureClassses.size()-1).getImsi(), imsi);
	}
	@Test
	public void getIMSIsWithEventIDsAndCauseCodesShouldReturnAListOfHelperObjects() throws Exception {
		BigInteger imsi = new BigInteger("240210000000013");
		imsisWithEventIDsAndCauseCodes = imsiDAO.getIMSIsWithEventIDsAndCauseCodes(imsi);
		assertNotNull(imsisWithEventIDsAndCauseCodes);
		assertFalse(imsisWithEventIDsAndCauseCodes.isEmpty());
		assertEquals(2, imsisWithEventIDsAndCauseCodes.size());
		
		imsi = new BigInteger("310560000000012");
		imsisWithEventIDsAndCauseCodes = imsiDAO.getIMSIsWithEventIDsAndCauseCodes(imsi);
		assertNotNull(imsisWithEventIDsAndCauseCodes);
		assertFalse(imsisWithEventIDsAndCauseCodes.isEmpty());
		assertEquals(3, imsisWithEventIDsAndCauseCodes.size());	
		
		imsi = new BigInteger("344930000000011");
		imsisWithEventIDsAndCauseCodes = imsiDAO.getIMSIsWithEventIDsAndCauseCodes(imsi);
		assertNotNull(imsisWithEventIDsAndCauseCodes);
		assertFalse(imsisWithEventIDsAndCauseCodes.isEmpty());
		assertEquals(4, imsisWithEventIDsAndCauseCodes.size());
	}
	@Test
	public void getAllBaseDataByIMSIShouldReturnAListOfBaseDataObjects() {
		BigInteger imsi = new BigInteger("344930000000011");
		baseDataList = imsiDAO.getAllBaseDataByIMSIAndDate(imsi, dateFour, dateFive);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(4, baseDataList.size());
		
	}
	@Test
	public void getIMSIsForFailureClassTest() throws Exception{
		String failureClass = "1";
		baseDataList = imsiDAO.getIMSIsForFailureClass(failureClass);
		assertFalse(baseDataList.isEmpty());
		assertEquals(3, baseDataList.size());
	}
	@Test
	public void getFailureClassTest() throws Exception{
		failureClassList = imsiDAO.getFailureClass();
		assertFalse(failureClassList.isEmpty());
		assertEquals(2, failureClassList.size());
	}
	
}
