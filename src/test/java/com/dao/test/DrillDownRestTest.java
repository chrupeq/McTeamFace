package com.dao.test;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.DrillDownDAO;
import com.ait.db.data.IMSIDAO;
import com.ait.db.model.Base_data;
import com.ait.db.rest.DrillDownRestService;
import com.ait.db.rest.IMSIRestService;
import com.ait.db.rest.JaxRsActivator;
import com.ait.imsiStats.IMSIStatsProducer;

@RunWith(Arquillian.class)
@RunAsClient
public class DrillDownRestTest {
	
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
        		.addPackage(DrillDownDAO.class.getPackage())
        		.addClasses(DrillDownRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test
	public void getFailureEventDescRestTest() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/drill_down/failure_event_desc");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
	}
	
	@Test
	public void getModelOfPhoneDescRestTest() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/drill_down/imsi_desc");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
	}

}
