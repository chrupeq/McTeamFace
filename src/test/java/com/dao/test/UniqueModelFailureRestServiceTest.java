package com.dao.test;

import static org.junit.Assert.*;

import java.io.File;
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
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.ait.db.data.UniqeModelFailuresDAO;
import com.ait.db.model.Base_data;
import com.ait.db.rest.JaxRsActivator;
import com.ait.db.rest.UETypeRestService;
import com.ait.db.rest.UniqueModelFailuresRestService;

@RunWith(Arquillian.class)
@RunAsClient
public class UniqueModelFailureRestServiceTest {
	
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment() {
		
		// Import Maven runtime dependencies
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackage(Base_data.class.getPackage())
        		.addPackage(UniqeModelFailuresDAO.class.getPackage())
        		.addClasses(UniqueModelFailuresRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test 
	@InSequence(2)
	public void getAllUniqueModelFailuresTest() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+ "/unique_model_failures/33000153");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The response is: " + response);
	}
	
	@Test 
	@InSequence(1)
	public void nonexistentTACCodeTest() throws Exception {
		// request a tac number that does not exist
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+ "/unique_model_failures/404"); 
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The to the second request is: " + response);
	}
}
