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

import com.ait.db.data.CauseCodeDAO;
import com.ait.db.data.EventCauseFailuresDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;
import com.ait.db.rest.CauseCodeRestService;
import com.ait.db.rest.EventCauseFailuresRestService;
import com.ait.db.rest.JaxRsActivator;

@RunWith(Arquillian.class)
@RunAsClient
public class CauseCodeRestServiceTest {
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addPackage(CauseCodeDAO.class.getPackage())
				.addClasses(CauseCodeRestService.class, JaxRsActivator.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test
	public void getUniqueCauseCodesFromBaseDataTableShouldReturn200() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+"/cause_codes/get_unique");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
	    ClientResponse<String> responseObj = request.get(String.class);
	    assertEquals(200, responseObj.getStatus());
	    String response = responseObj.getEntity().trim();
	    System.out.println("The response is: " + response);
	    assertEquals(response, "[0,11,12,13,23]");
	}
	@Test
	public void getAffectedIMSIsByCauseCodeShouldReturn200One() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+"/cause_codes/get_by_cause_code?cause_code=11");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
	    ClientResponse<String> responseObj = request.get(String.class);
	    assertEquals(200, responseObj.getStatus());
	    String response = responseObj.getEntity().trim();
	    System.out.println("The response is: " + response);
	    assertEquals(response, "[240210000000013,310560000000012,344930000000011]");
	}
	@Test
	public void getAffectedIMSIsByCauseCodeShouldReturn200Two() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+"/cause_codes/get_by_cause_code?cause_code=13");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
	    ClientResponse<String> responseObj = request.get(String.class);
	    assertEquals(200, responseObj.getStatus());
	    String response = responseObj.getEntity().trim();
	    System.out.println("The response is: " + response);
	    assertEquals(response, "[344930000000011]");
	}
	@Test
	public void getAffectedIMSIsByCauseCodeShouldReturn404() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+"/cause_codes/get_by_cause_code?cause_code=-15");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
	    ClientResponse<String> responseObj = request.get(String.class);
	    assertEquals(404, responseObj.getStatus());
	}
}
