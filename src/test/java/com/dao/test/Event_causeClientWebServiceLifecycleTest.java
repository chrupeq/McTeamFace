package com.dao.test;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Failure_class;
import com.ait.db.rest.JaxRsActivator;
import com.ait.db.rest.NetworkEntityRestService;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
@RunAsClient
public class Event_causeClientWebServiceLifecycleTest {
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;

	@Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Event_cause.class.getPackage())
        		.addPackage(NetworkEntityDAO.class.getPackage())
        		.addClasses(IMSIStats.class, NetworkEntityRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@ArquillianResource
    URL deploymentUrl;
	
	@Test 
	@InSequence(1)
	public void getAllEvent_causeEntities() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(2)
	public void testEvent_causeNotFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause/3/4097");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(3)
	public void testPostEvent_cause() throws Exception {
		jsonData = "{\"cause_code\":3,\"event_id\":4097,\"description\":\"RRC CONN SETUP-EUTRAN GENERATED REASON\"}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		
	}
	
	@Test
	@InSequence(4)
	public void testCreatedEvent_causeFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause/3/4097");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(5)
	public void testPutEvent_cause() throws Exception {
		jsonData = "{\"cause_code\":3,\"event_id\":4099,\"description\":\"RRC CONN SETUP-EUTRAN GENERATED REASON(Updated)\"}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause/3/4097");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.put(String.class);
		assertEquals(200, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		
	}
	
	@Test
	@InSequence(6)
	public void testChangedEvent_causeFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause/3/4099");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	@Test
	@InSequence(7)
	public void testEvent_causeDeleteById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause/3/4099");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.delete(String.class);
        assertEquals(204, responseObj.getStatus());
	}
	@Test
	@InSequence(8)
	public void testEvent_causeNotFoundAfterDeleteById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause/3/4099");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
	}

}
