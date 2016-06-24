package com.dao.test;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.rest.JaxRsActivator;

@RunWith(Arquillian.class)
@RunAsClient
public class UniqueModelFailureRestServiceTest {

	private ClientRequest request;
	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class).addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addClass(JaxRsActivator.class);
		return archive;
	}
	
	@Test 
	public void getAllUniqueModelFailuresTest() throws Exception {
		request = new ClientRequest("http://localhost:8080/GroupProject2016/rest/unique_model_failures/100100");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        System.out.println("GET /unique_model HTTP/get_all/ .1\n\n" + responseObj.getEntity());
        String response = responseObj.getEntity().trim();
	}
	
	@Test 
	public void getAllUniqueModelFailuresFailsWhenNoTacNumberTest() throws Exception {
		request = new ClientRequest("http://localhost:8080/GroupProject2016/rest/unique_model_failures");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
        System.out.println("GET /unique_model HTTP/get_all/ .1\n\n" + responseObj.getEntity());
        String response = responseObj.getEntity().trim();
	}
}
