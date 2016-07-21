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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.IMSIDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.TopTenMarketOperatorCellIdCombinations;
import com.ait.db.model.UniqueEventCauseFailureClass;
import com.ait.imsiStats.IMSIStats;


@RunWith(Arquillian.class)
public class UniqueEventCauseFailureClassDAOTest {
	
	private List<UniqueEventCauseFailureClass> uniqueEventCauseFailureClassList;
	private BigInteger imsi1;
	private BigInteger imsi2;
	private BigInteger imsi3;
	private BigInteger imsi4;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addPackage(IMSIDAO.class.getPackage())
				.addClass(IMSIStats.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@EJB
	IMSIDAO imsiDAO;
	@Before
	public void setUp(){
		imsi1 = new BigInteger("344930000000011");
		imsi2 = new BigInteger("310560000000012");
		imsi3 = new BigInteger("240210000000013");
		imsi4 = new BigInteger("000000000000000");
	}
	@Test
	public void getUniqueCauseCodeAndDescriptionForFailureClassForIMSIShouldReturnListPart1(){
		uniqueEventCauseFailureClassList = imsiDAO.getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(imsi1);
		assertNotNull(uniqueEventCauseFailureClassList);
		assertFalse(uniqueEventCauseFailureClassList.isEmpty());
		assertEquals(3, uniqueEventCauseFailureClassList.size());
		

	}
	
	@Test
	public void getUniqueCauseCodeAndDescriptionForFailureClassForIMSIShouldReturnListPart2(){
		uniqueEventCauseFailureClassList = imsiDAO.getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(imsi2);
		assertNotNull(uniqueEventCauseFailureClassList);
		assertFalse(uniqueEventCauseFailureClassList.isEmpty());
		assertEquals(3, uniqueEventCauseFailureClassList.size());
		

	}
	
	@Test
	public void getUniqueCauseCodeAndDescriptionForFailureClassForIMSIShouldReturnListPart3(){
		uniqueEventCauseFailureClassList = imsiDAO.getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(imsi3);
		assertNotNull(uniqueEventCauseFailureClassList);
		assertFalse(uniqueEventCauseFailureClassList.isEmpty());
		assertEquals(2, uniqueEventCauseFailureClassList.size());
		

	}
	

	@Test
	public void getUniqueCauseCodeAndDescriptionForFailureClassForIMSIShouldReturnAnEmptyList(){
		uniqueEventCauseFailureClassList = imsiDAO.getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(imsi4);
		assertNotNull(uniqueEventCauseFailureClassList);
		assertTrue(uniqueEventCauseFailureClassList.isEmpty());
	}

	
}
