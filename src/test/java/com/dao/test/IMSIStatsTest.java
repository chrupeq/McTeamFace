package com.dao.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.ait.db.data.CompositePrimaryKeyType;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.NetworkEntity;
import com.ait.imsiStats.IMSIStatsProducer;

@RunWith(Arquillian.class)
public class IMSIStatsTest {
	private List<? extends NetworkEntity> baseDataList;
	private IMSIStatsProducer imsiStatsProducer;
	private Map<BigInteger, Integer> numOfFailures;
	private Map<BigInteger, Integer> durationOfFailures;
	private BigInteger testIMSI;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(IMSIStatsProducer.class.getPackage())
				.addPackage(Base_data.class.getPackage())
				.addClasses(NetworkEntityDAO.class, NetworkEntityType.class, NetworkEntity.class, CompositePrimaryKeyType.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml").addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@EJB
	NetworkEntityDAO networkEntityDAO;
	
	@Before
	public void setUp() {
		baseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA);
		imsiStatsProducer = new IMSIStatsProducer(baseDataList);
	}

	@Test
	public void testThatTheBaseDataListIsPassedToTheIMSIStatsProducerObject() {
		assertEquals(10, imsiStatsProducer.getLengthOfList());
	}
	@Test
	public void testThatTheMapOfIMSIsIsCorrect() {
		assertFalse(imsiStatsProducer.countTheNumberOfFailures().isEmpty());
		assertEquals(3, imsiStatsProducer.countTheNumberOfFailures().size());
	}
	@Test
	public void testNumberOfFailures() {
		numOfFailures = imsiStatsProducer.countTheNumberOfFailures();
		testIMSI = new BigInteger("344930000000011");
		assertTrue(numOfFailures.containsKey(testIMSI));
		assertEquals(new Integer(4), numOfFailures.get(testIMSI));
		
		testIMSI = new BigInteger("310560000000012");
		assertTrue(numOfFailures.containsKey(testIMSI));
		assertEquals(new Integer(3), numOfFailures.get(testIMSI));
		
		testIMSI = new BigInteger("240210000000013");
		assertTrue(numOfFailures.containsKey(testIMSI));
		assertEquals(new Integer(3), numOfFailures.get(testIMSI));
		
		testIMSI = new BigInteger("100000000000000");
		assertFalse(numOfFailures.containsKey(testIMSI));	
	}
	@Test
	public void numberOfFailuresMapShouldBeEmptyIfBaseDataListIsEmpty() {
		baseDataList = new ArrayList<Base_data>(0);
		imsiStatsProducer = new IMSIStatsProducer(baseDataList);
		numOfFailures = imsiStatsProducer.countTheNumberOfFailures();
		assertNotNull(numOfFailures);
		assertTrue(numOfFailures.isEmpty());
		
	}
	@Test
	public void numberOfFailuresMapShouldBeEmptyIfBaseDataListDoedNotContainBaseDataObjects() {
		baseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.USER_EQUIPMENT);
		assertFalse(baseDataList.isEmpty());
		imsiStatsProducer = new IMSIStatsProducer(baseDataList);
		numOfFailures = imsiStatsProducer.countTheNumberOfFailures();
		assertNotNull(numOfFailures);
		assertTrue(numOfFailures.isEmpty());
	}
	
	@Test
	public void testTotalFailureTimeCalculation() {
		durationOfFailures = imsiStatsProducer.calculateTotalFailureTime();
		assertFalse(durationOfFailures.isEmpty());
		assertEquals(3, durationOfFailures.size());
		
		testIMSI = new BigInteger("344930000000011");
		assertTrue(durationOfFailures.containsKey(testIMSI));
		assertEquals(new Integer(4000), durationOfFailures.get(testIMSI));
		
		testIMSI = new BigInteger("310560000000012");
		assertTrue(durationOfFailures.containsKey(testIMSI));
		assertEquals(new Integer(3000), durationOfFailures.get(testIMSI));
		
		testIMSI = new BigInteger("240210000000013");
		assertTrue(durationOfFailures.containsKey(testIMSI));
		assertEquals(new Integer(3000), durationOfFailures.get(testIMSI));
	}
	@Test
	public void shouldReturnAnEmptyMapIfFailureMapEmpty() {
		baseDataList = new ArrayList<Base_data>(0);
		imsiStatsProducer = new IMSIStatsProducer(baseDataList);
		durationOfFailures = imsiStatsProducer.calculateTotalFailureTime();
		assertNotNull(durationOfFailures);
		assertTrue(durationOfFailures.isEmpty());
	}
	@Test
	public void shouldReturnAnEmptyMapIfBaseDataListDoesNotContainBaseDataObjects() {
		baseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.USER_EQUIPMENT);
		assertFalse(baseDataList.isEmpty());
		imsiStatsProducer = new IMSIStatsProducer(baseDataList);
		durationOfFailures = imsiStatsProducer.calculateTotalFailureTime();
		assertNotNull(durationOfFailures);
		assertTrue(durationOfFailures.isEmpty());
	}
	
	
}

