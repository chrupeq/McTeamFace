package com.gui.test.user;

import static org.junit.Assert.*;
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

import com.ait.db.data.SessionDAO;
import com.ait.db.model.Base_data;
import com.ait.db.rest.JaxRsActivator;
import com.ait.gui.User;
import com.ait.gui.UserWS;
import com.ait.gui.UsersDAO;

@RunWith(Arquillian.class)
@RunAsClient
public class UserIntegrationTest {

	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value()
			.substring(1);
	private ClientRequest request;
	private ClientResponse<String> responseObj;
	private String jsonData;

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(User.class.getPackage())
				.addClasses(JaxRsActivator.class, SessionDAO.class).addPackage(Base_data.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml").addAsResource("import.sql")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@ArquillianResource
	URL deploymentUrl;

	@EJB
	private UserWS userWS;

	@EJB
	private UsersDAO userDAO;

	@Test
	@InSequence(1)
	public void shouldGetAllUsersInData() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/users");
		request.header("Accept", MediaType.APPLICATION_JSON);
		responseObj = request.get(String.class);
		assertEquals(200, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		assertEquals(
				"[{\"id\":1,\"username\":\"Suncho\",\"password\":\"nullypointer\",\"firstname\":\"Mitja\",\"job_title\":\"SE\",\"lastname\""
						+ ":\"Suncic\"},{\"id\":2,\"username\":\"FairyMonkey\",\"password\":\"princess\",\"firstname\":\"Laura\",\"job_title\":\"CSR\",\"lastname\":"
						+ "\"Hunt\"},{\"id\":3,\"username\":\"TheBest\",\"password\":\"TheBestest\",\"firstname\":\"Ruaidhri\",\"job_title\":\"SA\",\"lastname\":\"Garrett\"}]",
				response);
	}

	@Test
	@InSequence(2)
	public void shouldGetOneUserInData() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/users/1");
		request.header("Accept", MediaType.APPLICATION_JSON);
		responseObj = request.get(String.class);
		assertEquals(200, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		assertEquals(
				"{\"id\":1,\"username\":\"Suncho\",\"password\":\"nullypointer\",\"firstname\":\"Mitja\",\"job_title\":\"SE\",\"lastname\":\"Suncic\"}",
				response);
	}

	@Test
	@InSequence(3)
	public void shouldPersistValidNewUserInData() throws Exception {
		jsonData = "{\"username\":\"BELTERS\",\"password\":\"PorkBelly\",\"firstname\":\"Lukasz\",\"job_title\":\"NME\",\"lastname\":\"Stanowski\"}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/users");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.post(String.class);
		assertEquals(201, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		assertEquals(
				"{\"id\":4,\"username\":\"BELTERS\",\"password\":\"PorkBelly\",\"firstname\":\"Lukasz\",\"job_title\":\"NME\",\"lastname\":\"Stanowski\"}",
				response);
	}

	@Test
	@InSequence(4)
	public void shouldUpdateValidUserInData() throws Exception {
		jsonData = "{\"username\":\"NOBELTERS\",\"password\":\"PorkBelly\",\"firstname\":\"Lukasz\",\"job_title\":\"NME\",\"lastname\":\"Stanowski\"}";
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/users/4");
		request.accept("application/json").body(MediaType.APPLICATION_JSON, jsonData);
		responseObj = request.put(String.class);
		assertEquals(200, responseObj.getStatus());
		String response = responseObj.getEntity().trim();
		System.out.println("The response is: " + response);
		assertEquals(
				"{\"id\":4,\"username\":\"NOBELTERS\",\"password\":\"PorkBelly\",\"firstname\":\"Lukasz\",\"job_title\":\"NME\",\"lastname\":\"Stanowski\"}",
				response);
	}

	@Test
	@InSequence(5)
	public void shouldDeleteValidUserInData() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/users/4");
		request.header("Accept", MediaType.APPLICATION_JSON);
		responseObj = request.delete(String.class);
		assertEquals(204, responseObj.getStatus());
		assertNull(responseObj.getEntity());

	}

}
