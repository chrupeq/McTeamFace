package com.dao.test;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.text.ParseException;
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

import com.ait.db.data.DrillDownDAO;
import com.ait.db.model.Base_data;

@RunWith(Arquillian.class)
public class DrillDownDAOTest {
	private List<Base_data> baseDataList;


	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addClass(DrillDownDAO.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@EJB
	DrillDownDAO drillDownDAO;
	
	@Test
	public void getFailureEventDescTest() throws ParseException {
		baseDataList = drillDownDAO.getFailureEventDesc(4097, 13, 21060800);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(1, baseDataList.size());
	}
	
	@Test
	public void getModelOfPhoneDescTest() throws ParseException {
		BigInteger imsi = BigInteger.valueOf(344930000000011L);
		baseDataList = drillDownDAO.getModelOfPhoneDesc(imsi);
		assertNotNull(baseDataList);
		assertFalse(baseDataList.isEmpty());
		assertEquals(6, baseDataList.size());
	}
	
}
