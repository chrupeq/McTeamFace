package com.dao.test;

import static org.junit.Assert.*;

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

import com.ait.db.data.IMSIDAO;
import com.ait.db.model.Base_data;
import com.ait.db.rest.IMSIRestService;
import com.ait.db.rest.JaxRsActivator;
import com.ait.imsiStats.IMSIStatsProducer;

@RunWith(Arquillian.class)
@RunAsClient
public class IMSIRestServiceTest {
	
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
        		.addPackage(IMSIStatsProducer.class.getPackage())
        		.addPackage(IMSIDAO.class.getPackage())
        		.addClasses(IMSIRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test
	public void testGetUniqueIMSI() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/get_unique");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals("[344930000000011,240210000000013,310560000000012]", response);
	}
	@Test
	public void testGetIMSIsForStats200ResponseOne() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/get_stats?dateOne=11/01/2013+17:00&dateTwo=11/01/2013+17:30");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals("[{\"imsi\":240210000000013,"
        		+ "\"failureDuration\":3000,\"numberOfFailures\":3}"
        		+ ",{\"imsi\":310560000000012,\"failureDuration\":3000,"
        		+ "\"numberOfFailures\":3},{\"imsi\":344930000000011,"
        		+ "\"failureDuration\":4000,\"numberOfFailures\":4}]", 
        response);
	}
	@Test
	public void testGetIMSIsForStats200ResponseTwo() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/get_stats?dateOne=11/01/2013+17:15&dateTwo=11/01/2013+17:16");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals("[{\"imsi\":310560000000012,\"failureDuration\":3000,"
        		+ "\"numberOfFailures\":3},{\"imsi\":344930000000011,"
        		+ "\"failureDuration\":4000,\"numberOfFailures\":4}]", 
        		response);
	}
	@Test
	public void testGetIMSIsForStats200ResponseThree() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/get_stats?dateOne=11/01/2013+17:16&dateTwo=11/01/2013+17:17");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals("[{\"imsi\":240210000000013,\"failureDuration\":3000,"
        		+ "\"numberOfFailures\":3},{\"imsi\":310560000000012,"
        		+ "\"failureDuration\":3000,\"numberOfFailures\":3}]", response);
	}
	@Test
	public void testGetIMSIsForStats200ResponseFour() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/get_stats?dateOne=11/01/2013+17:15&dateTwo=11/01/2013+17:15");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals("[{\"imsi\":344930000000011,\"failureDuration\":4000,\"numberOfFailures\":4}]", response);
	}
	@Test
	public void testGetIMSIsForStatsShouldReturn404() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/get_stats?dateOne=01/01/1988+17:00&dateTwo=02/01/1988+17:30");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
	}
	
	@Test
	public void getIMSIsBetweenDatesShouldReturn200() throws Exception {
		
		request = new ClientRequest(deploymentUrl.toString() + 
				RESOURCE_PREFIX + "/imsi/get_imsis_between_dates?date1=11/01/2013+17:00&date2=11/01/2013+17:30");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals(200, responseObj.getStatus());
	}
	@Test
	public void getIMSIsBetweenDatesShouldReturn404() throws Exception {
		// pass in two dates that cannot possibly be in the database
		request = new ClientRequest(deploymentUrl.toString() + 
				RESOURCE_PREFIX + "/imsi/get_imsis_between_dates?date1=01/01/1988+17:00&date2=02/01/1988+17:30");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals(404, responseObj.getStatus());
	}
	@Test
	public void getIMSIsWithEventIDAndCauseCodeShouldReturn200() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + 
				RESOURCE_PREFIX + "/imsi/imsi_event_id/240210000000013");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
		ClientResponse<String> responseObj = request.get(String.class);
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals(200, responseObj.getStatus());
	}
	@Test
	public void getIMSIsWithEventIDAndCauseCodeShouldReturn404() throws Exception {
		// pass in a non-existent imsi code
		request = new ClientRequest(deploymentUrl.toString() + 
				RESOURCE_PREFIX + "/imsi/imsi_event_id/404");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
		ClientResponse<String> responseObj = request.get(String.class);
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals(404, responseObj.getStatus());
	}
	@Test
	public void getIMSIsWithEventIDAndCauseCodeWhenAttributesAreNull() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + 
				RESOURCE_PREFIX + "/imsi/imsi_event_id/344930000000011");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
		ClientResponse<String> responseObj = request.get(String.class);
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
        assertEquals(200, responseObj.getStatus());
        assertEquals("[{\"event_id\":4125,\"cause_code\":\"23\",\"failure_class\":\"0\",\"imsi\":344930000000011},"
        		+ "{\"event_id\":4106,\"cause_code\":\"11\",\"failure_class\":\"1\",\"imsi\":344930000000011},"
        		+ "{\"event_id\":4097,\"cause_code\":\"13\",\"failure_class\":\"1\",\"imsi\":344930000000011},"
        		+ "{\"event_id\":-1,\"cause_code\":\"Cause code could not be determined\","
        		+ "\"failure_class\":\"Failure class could not be determined.\",\"imsi\":344930000000011}]", response);
	}
}
