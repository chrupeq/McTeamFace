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
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.IMSI;
import com.ait.db.rest.IMSIRestService;
import com.ait.db.rest.JaxRsActivator;
import com.ait.db.rest.NetworkEntityRestService;
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
        		.addPackage(IMSI.class.getPackage())
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
	public void testGetIMSIsForStats() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/get_stats");
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
}