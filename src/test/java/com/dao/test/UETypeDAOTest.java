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

import com.ait.db.data.UETypeDAO;
import com.ait.db.model.User_equipment;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
public class UETypeDAOTest {
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(User_equipment.class.getPackage())
				.addPackage(UETypeDAO.class.getPackage())
				.addClass(IMSIStats.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	UETypeDAO ueTypeDAO;
	
	@Test
	public void testThatOnlyUniqueValuesRetrieved() {
		List<User_equipment> ueList = ueTypeDAO.getAllUniqueModels();
		assertFalse(ueList.isEmpty());
		assertEquals(4, ueList.size());
	}
}
