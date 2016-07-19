package com.dao.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
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
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.TopTenDAO;
import com.ait.db.model.Base_data;
import com.ait.db.rest.IMSIRestService;
import com.ait.db.rest.JaxRsActivator;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
@RunAsClient
public class TopTenRestServiceTest {
	
	private static final String RESOURCE_PREFIX = JaxRsActivator.class.getAnnotation(ApplicationPath.class).value().substring(1);
	private ClientRequest request;
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment() {
		// Import Maven runtime dependencies
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Base_data.class.getPackage())
        		.addPackage(TopTenDAO.class.getPackage())
        		.addClasses(IMSIStats.class, IMSIRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(files);
	}
	@ArquillianResource
    URL deploymentUrl;
	
	@Test
	public void getTopTenMarketOperatorCellIdCombinationsWithFailuresShouldReturnA200() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/"
				+ "top10_market_operator_cellIdCombinations?dateOne=11/01/2013+17:00&dateTwo=11/01/2013+17:30");
		request.header("Accept", MediaType.APPLICATION_JSON);
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(200, responseObj.getStatus());
	}
	@Test
	public void getTopTenMarketOperatorCellIdCombinationsWithFailuresShouldReturnA404() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/"
				+ "top10_market_operator_cellIdCombinations?dateOne=11/01/1970+17:00&dateTwo=11/01/1971+17:30");
			request.header("Accept", MediaType.APPLICATION_JSON);
			// we're expecting a String back
	       ClientResponse<String> responseObj = request.get(String.class);
	       assertEquals(404, responseObj.getStatus());
		}
		@Test
		public void getTopTenIMSIShouldReturn200() throws Exception{
			request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/"
					+ "top10_IMSI?dateOne=11/01/2013+17:00&dateTwo=11/01/2013+17:30");
			request.header("Accept", MediaType.APPLICATION_JSON);
			// we're expecting a String back
	        ClientResponse<String> responseObj = request.get(String.class);
	        assertEquals(200, responseObj.getStatus());
		}
	@Test
	public void getTopTenIMSIShouldReturn404() throws Exception{
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX + "/imsi/"
				+ "top10_IMSI?dateOne=11/01/1970+17:00&dateTwo=11/01/1971+17:30");
			request.header("Accept", MediaType.APPLICATION_JSON);
			// we're expecting a String back
	        ClientResponse<String> responseObj = request.get(String.class);
	        assertEquals(404, responseObj.getStatus());
	}

}
