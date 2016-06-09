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
import com.ait.db.model.Base_data;
import com.ait.db.rest.JaxRsActivator;
import com.ait.db.rest.NetworkEntityRestService;

@RunWith(Arquillian.class)
@RunAsClient
public class Base_dataClientWebServiceLifecycleTest {
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;

	@Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Base_data.class.getPackage())
        		.addPackage(NetworkEntityDAO.class.getPackage())
        		.addClasses(NetworkEntityRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test 
	@InSequence(1)
	public void getAllBase_dataEntities() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(2)
	public void testBase_dataNotFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data/2");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(3)
	public void testPostBase_data() throws Exception {
		jsonData = "{\"date_time\":\"2016-11-30\","
				+ "\"eventCause\":{\"cause_code\":0,\"event_id\":4097},"
				+ "\"failureClass\":{\"failure_class\":1},"
				+ "\"userEquipment\":{\"tac\":100100},"
				+ "\"mccMnc\":{\"mcc\":238,\"mnc\":1},"
				+ "\"cell_id\":4,\"duration\":1000,"
				+ "\"user_equipment\":{\"tac\":\"100100\"},"
				+ "\"ne_version\": \"11b\",\"imsi\":344930000000011,"
				+ "\"hier3_id\":4809532081614990000,\"hier32_id\":8226896360947470000,"
				+ "\"hier321_id\":1150444940909480000}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		
	}
	
	@Test
	@InSequence(4)
	public void testCreatedBase_dataFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data/2");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test
	@InSequence(5)
	public void testPutBase_data() throws Exception {
		jsonData = "{\"date_time\":\"2016-11-29\","
				+ "\"eventCause\":{\"cause_code\":0,\"event_id\":4097},"
				+ "\"failureClass\":{\"failure_class\":2},"
				+ "\"userEquipment\":{\"tac\":100100},"
				+ "\"mccMnc\":{\"mcc\":238,\"mnc\":1},"
				+ "\"cell_id\":4,\"duration\":1000,"
				+ "\"user_equipment\":{\"tac\":\"100100\"},"
				+ "\"ne_version\": \"11b\",\"imsi\":344930000000011,"
				+ "\"hier3_id\":4809532081614990000,\"hier32_id\":8226896360947470000,"
				+ "\"hier321_id\":1150444940909480000}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data/2");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.put(String.class);
		assertEquals(200, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		
	}
	
	@Test
	@InSequence(6)
	public void testChangedBase_dataFoundById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data/3");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	@Test
	@InSequence(7)
	public void testBase_dataDeleteById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data/3");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.delete(String.class);
        assertEquals(204, responseObj.getStatus());
	}
	@Test
	@InSequence(8)
	public void testBase_dataNotFoundAfterDeleteById() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data/3");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
	}
}
