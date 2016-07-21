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
import com.ait.db.model.User_equipment;
import com.ait.db.rest.JaxRsActivator;
import com.ait.db.rest.NetworkEntityRestService;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
@RunAsClient
public class User_equipmentClientWebServiceLifecycleTest {
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;

	@Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(User_equipment.class.getPackage())
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
	public void getAllUser_equipmentEntities() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(2)
	public void testUser_equipmentNotFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment/100900");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(3)
	public void testPostUser_equipment() throws Exception {
		jsonData = "{\"tac\":100900,\"marketing_name\":\"Test IMEI\",\"manufacturer\":\"Sony\","
				+ "\"access_capability\":\"GSM 1900\",\"model\":\"Test IMEI\",\"vendor_name\":\"Sony\","
				+ "\"user_equipment_type\":null,\"operating_system\":null,\"input_mode\":null}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		
	}
	
	@Test
	@InSequence(4)
	public void testCreatedUser_equipmentFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment/100900");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(5)
	public void testPutUser_equipment() throws Exception {
		jsonData = "{\"tac\":100800,\"marketing_name\":\"Test IMEI\",\"manufacturer\":\"Sony\","
				+ "\"access_capability\":\"GSM 1900\",\"model\":\"Test IMEI\",\"vendor_name\":\"Sony\","
				+ "\"user_equipment_type\":null,\"operating_system\":null,\"input_mode\":null}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment/100900");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.put(String.class);
		assertEquals(200, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		
	}
	
	@Test
	@InSequence(6)
	public void testChangedUser_equipmentFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment/100800");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	@Test
	@InSequence(7)
	public void testUser_equipmentDeleteById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment/100800");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.delete(String.class);
        assertEquals(204, responseObj.getStatus());
	}
	@Test
	@InSequence(8)
	public void testEvent_causeNotFoundAfterDeleteById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment/100800");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
	}
}
