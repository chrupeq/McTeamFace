package com.dao.test;

import static org.junit.Assert.*;

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
import com.ait.db.data.TopTenDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.TopTenMarketOperatorCellIdCombinations;

@RunWith(Arquillian.class)
public class TopTenDAOTest {
	private String dateOne;
	private String dateTwo;
	private String dateThree;
	private String dateFour;
	private String dateFive;
	private String dateSix;
	private String dateSeven;
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addPackage(TopTenDAO.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
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
	@EJB
	TopTenDAO topTenDAO;
	
	@Test
	public void getTopTenMarketOperatorCellIdCombinationsWithFailuresShouldReturnAList(){
		
		List<TopTenMarketOperatorCellIdCombinations> topTenList = topTenDAO.getTopTenMarketOperatorCellIdCombinationsWithFailures(dateOne, dateTwo);
		assertNotNull(topTenList);
		assertFalse(topTenList.isEmpty());
		assertEquals(3, topTenList.size());		
	}

}
