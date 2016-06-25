package com.dao.test;

import static org.junit.Assert.fail;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Base_data;
import com.ait.db.rest.JaxRsActivator;
import com.ait.db.rest.NetworkEntityRestService;
import com.ait.imsiStats.IMSIStatsProducer;

@RunWith(Arquillian.class)
public class IMSIStatsTest {
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(IMSIStatsProducer.class.getPackage())
				.addClasses(NetworkEntityRestService.class, JaxRsActivator.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml").addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
}

