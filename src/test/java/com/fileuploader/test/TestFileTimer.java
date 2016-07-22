package com.fileuploader.test;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import javax.ejb.EJB;
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

import com.ait.db.data.DateParser;
import com.ait.db.data.ParseDate;
import com.ait.db.data.SessionDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.IMSIWithFailuresFactory;
import com.ait.db.rest.JaxRsActivator;
import com.fileuploader.FileTimer;
import com.fileuploader.FileTimerDAO;
import com.fileuploader.FileTimerWS;

@RunWith(Arquillian.class)
@RunAsClient
public class TestFileTimer {
	
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value()
			.substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
				.addClasses(DateParser.class, ParseDate.class, JaxRsActivator.class, SessionDAO.class, FileTimer.class, FileTimerWS.class, FileTimerDAO.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@ArquillianResource
	URL deploymentUrl;

	@EJB
	private FileTimerDAO fileTimerDAO;
	
	@Test
	@InSequence(1)
	public void shouldGetOneFileTimeInData() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/fileTimer/1");
		request.header("Accept", MediaType.APPLICATION_JSON);
		responseObj = request.get(String.class);
		assertEquals(200, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
//		assertEquals(
//				"{\"id\":1,\"start_time\":\"Thu Jul 07 10:58:51 BST 2016\",\"end_time\":\"Thu Jul 07 10:58:58 BST 2016\"}",
//				response);
	}
}
