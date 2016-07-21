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
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.UniqeModelFailuresDAO;
import com.ait.db.model.Base_data;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
public class UniqueModelFailuresDAOTest {
	
	private int tacNumber;
	private List<Base_data> baseDataList;
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addPackage(UniqeModelFailuresDAO.class.getPackage())
				.addClass(IMSIStats.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@EJB
	UniqeModelFailuresDAO uniqueModelFailuresDAO;
	
//	@Test
//	public void testRightNumberOfUserEquipmentIsRetuerned() {
//		tacNumber = 21060800;
//		baseDataList = uniqueModelFailuresDAO.getAllUniqueModels(tacNumber);
//		assertFalse(baseDataList.isEmpty());
//		assertEquals(3, baseDataList.size());
//		
//		tacNumber = 33000153;
//		baseDataList = uniqueModelFailuresDAO.getAllUniqueModels(tacNumber);
//		assertEquals(3, baseDataList.size());
//		
//		tacNumber = 33000253;
//		baseDataList = uniqueModelFailuresDAO.getAllUniqueModels(tacNumber);
//		assertEquals(3, baseDataList.size());
//		
//		tacNumber = 100100;
//		baseDataList = uniqueModelFailuresDAO.getAllUniqueModels(tacNumber);
//		assertEquals(1, baseDataList.size());
//	}
//	
	@Test
	public void testNothingReturnedWhenTACWrong() {
		tacNumber = 404;
		baseDataList = uniqueModelFailuresDAO.getAllUniqueModels(tacNumber);
		assertTrue(baseDataList.isEmpty());	
	}	
}
