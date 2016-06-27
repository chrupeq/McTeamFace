package com.dao.test;

import static org.junit.Assert.*;

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

import junit.framework.Assert;

@RunWith(Arquillian.class)
@RunAsClient
public class NetworkEntityClientTest {
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;
	
	@Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackage(Base_data.class.getPackage())
        		.addPackage(NetworkEntityDAO.class.getPackage())
        		.addClasses(NetworkEntityRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test
	@InSequence(9)
	public void getBaseDataIdByUsingClientRequest() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data/2");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        System.out.println("GET /network_entities/get_by_id/1 HTTP/1.1\n\n" + responseObj.getEntity());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	@Test 
	@InSequence(2)
	public void getAllNetworkEntities() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/base_data");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        System.out.println("GET /network_entities/base_data HTTP/get_all/ .1\n\n" + responseObj.getEntity());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	@Test
	@InSequence(3)
	public void testResourceNotFoundByURI() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/4");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
        System.out.println("GET /network_entities/4 HTTP/1.1\n\n" + responseObj.getEntity());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	@Test
	@InSequence(4)
	public void testPostFailureClass() throws Exception {
		// insert into the failure_class table
		jsonData = "{\"failure_class\":\"2\", \"description\":\"MT ACCESS\"}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/failure_class");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		assertEquals("{\"failure_class\":2,\"description\":\"MT ACCESS\"}", response);
	}
	@Test
	@InSequence(5)
	public void testPostMccMnc() throws Exception {
		// insert into the mcc_mnc table
		jsonData = "{\"mcc\":238,\"mnc\":3,\"country\":\"Denmark\",\"operator\":\"MIGway A/S DK\"}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/mcc_mnc");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		assertEquals("{\"mcc\":238,\"mnc\":3,\"country\":\"Denmark\",\"operator\":\"MIGway A/S DK\"}", response);
	}
	@Test
	@InSequence(6)
	public void testPostEventCause() throws Exception {
		// insert into the event_cause table
		jsonData = "{\"cause_code\":3,\"event_id\":4097,\"description\":\"RRC CONN SETUP-EUTRAN GENERATED REASON\"}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/event_cause");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		assertEquals("{\"cause_code\":3,\"event_id\":4097,\"description\":\"RRC CONN SETUP-EUTRAN GENERATED REASON\"}", response);
	}
	@Test
	@InSequence(7)
	public void testPostUserEquipment() throws Exception {
		// insert into the user_equipment table
		jsonData = "{\"tac\":100900,\"marketing_name\":\"Test IMEI\",\"manufacturer\":\"Sony\","
				+ "\"access_capability\":\"GSM 1900\",\"model\":\"Test IMEI\",\"vendor_name\":\"Sony\","
				+ "\"user_equipment_type\":null,\"operating_system\":null,\"input_mode\":null}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/network_entities/user_equipment");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		assertEquals("{\"tac\":100900,\"marketing_name\":\"Test IMEI\",\"manufacturer\":\"Sony\","
				+ "\"access_capability\":\"GSM 1900\",\"model\":\"Test IMEI\",\"vendor_name\":\"Sony\","
						+ "\"user_equipment_type\":null,\"operating_system\":null,\"input_mode\":null}", response);
	}
	@Test
	@InSequence(8)
	public void testPostBaseData() throws Exception {
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
		
}
