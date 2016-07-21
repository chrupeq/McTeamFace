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

import com.ait.db.data.DateParser;
import com.ait.db.data.DrillDownDAO;
import com.ait.db.data.ParseDate;
import com.ait.db.model.Base_data;

@RunWith(Arquillian.class)
public class DrillDownDAOTest {
	private List<Base_data> baseDataList;
	private String dateOne = "2013-01-11 17:15:00";
	private String dateTwo = "2013-01-11 17:16:00";
	private String dateThree = "2013-01-11 17:17:00";
	private String dateFour = "2013-01-11 17:00:00";
	private String dateFive = "2013-01-11 17:30:00";
	private String dateSix = "2016-06-28 00:00:00";
	private String dateSeven = "2016-06-28 23:59:00";
	private long durationOne = 1;
	private long durationTwo = 100000;


	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addClasses(DrillDownDAO.class, ParseDate.class, DateParser.class)
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
		assertEquals(4, baseDataList.size());
	}
}
