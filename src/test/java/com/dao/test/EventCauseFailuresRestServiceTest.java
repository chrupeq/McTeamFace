package com.dao.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
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
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.EventCauseFailuresDAO;
import com.ait.db.model.Event_cause;
import com.ait.db.rest.EventCauseFailuresRestService;
import com.ait.db.rest.IMSIRestService;
import com.ait.db.rest.JaxRsActivator;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
@RunAsClient
public class EventCauseFailuresRestServiceTest {
	
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment() {
		// Import Maven runtime dependencies
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Event_cause.class.getPackage())
				.addPackage(EventCauseFailuresDAO.class.getPackage())
				.addClasses(IMSIStats.class, EventCauseFailuresRestService.class, JaxRsActivator.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	            .addAsLibraries(files);
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test
	public void getEventCauseFailuresForATacShouldReturn200One() throws Exception {
	request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
			+ "/event_cause_failures/count_for_tac?tac=33000253");
	request.header("Accept", MediaType.APPLICATION_JSON);
	// we're expecting a String back
    ClientResponse<String> responseObj = request.get(String.class);
    assertEquals(200, responseObj.getStatus());
    String response = responseObj.getEntity().trim();
    System.out.println("The response is: " + response);
    assertEquals(response, "[{\"event_id\":4098,\"cause_code\":0,\"numberOfFailures\":2},"
    		+ "{\"event_id\":4106,\"cause_code\":11,\"numberOfFailures\":1}]");
	}
	@Test
	public void getEventCauseFailuresForATacShouldReturn200Two() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+ "/event_cause_failures/count_for_tac?tac=33000153");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
	    ClientResponse<String> responseObj = request.get(String.class);
	    assertEquals(200, responseObj.getStatus());
	    String response = responseObj.getEntity().trim();
	    System.out.println("The response is: " + response);
	    assertEquals(response,"[{\"event_id\":4098,\"cause_code\":0,\"numberOfFailures\":1},"
	    		+ "{\"event_id\":4106,\"cause_code\":11,\"numberOfFailures\":1},"
	    		+ "{\"event_id\":4097,\"cause_code\":12,\"numberOfFailures\":1}]");
	}
	@Test
	public void getEventCauseFailuresForATacShouldReturn404() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+ "/event_cause_failures/count_for_tac?tac=100000");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
	    ClientResponse<String> responseObj = request.get(String.class);
	    assertEquals(404, responseObj.getStatus());
	}
}
