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
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.rest.JaxRsActivator;

@RunWith(Arquillian.class)
@RunAsClient
public class UETypeRestServiceTest {

	private ClientRequest request;
	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class).addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addClass(JaxRsActivator.class);
		return archive;
	}
	
	@Test 
	public void getAllManufacturersAndModelsTest() throws Exception {
		request = new ClientRequest("http://localhost:8080/GroupProject2016/rest/unique_model");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        System.out.println("GET /unique_model HTTP/get_all/ .1\n\n" + responseObj.getEntity());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
}
