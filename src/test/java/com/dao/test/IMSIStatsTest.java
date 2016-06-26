package com.dao.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
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
		
		
	}
	
	
}

