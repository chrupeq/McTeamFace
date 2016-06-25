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
		return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackage(Base_data.class.getPackage())
        		.addPackage(UniqeModelFailuresDAO.class.getPackage())
        		.addClasses(UniqueModelFailuresRestService.class, JaxRsActivator.class)
        		.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
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
        assertEquals(response, "[{\"report_id\":3,\"date_time\":1357924560000,"
        		+ "\"event_cause\":{\"cause_code\":0,\"event_id\":4098,"
        		+ "\"description\":\"S1 SIG CONN SETUP-SUCCESS\"},\"failure_class\":"
        		+ "{\"failure_class\":1,\"description\":\"HIGH PRIORITY ACCESS\"},"
        		+ "\"user_equipment\":{\"tac\":33000153,\"marketing_name\":\"9109 PA\","
        		+ "\"manufacturer\":\"Alcatel Radiotelephone (LAVAL)\","
        		+ "\"access_capability\":\"GSM 900\",\"model\":\"9109 PA\","
        		+ "\"vendor_name\":\"Alcatel Radiotelephone (LAVAL)\","
        		+ "\"user_equipment_type\":null,\"operating_system\":null,\"input_mode\":null},"
        		+ "\"mcc_mnc\":{\"mcc\":310,\"mnc\":560,\"country\":\"United States of America\","
        		+ "\"operator\":\"AT&T Mobility\"},\"cell_id\":4,\"duration\":1000,"
        		+ "\"ne_version\":\"11b\",\"imsi\":310560000000012,"
        		+ "\"hier3_id\":4809532081614990000,\"hier32_id\":8226896360947470000,"
        		+ "\"hier321_id\":1150444940909480000},"
        		+ "{\"report_id\":4,\"date_time\":1357924560000,"
        		+ "\"event_cause\":{\"cause_code\":12,\"event_id\":4097,"
        		+ "\"description\":\"RRC CONN SETUP-REJECT DUE TO OVERLOAD\"},"
        		+ "\"failure_class\":{\"failure_class\":1,\"description\":\"HIGH PRIORITY ACCESS\"},"
        		+ "\"user_equipment\":{\"tac\":33000153,\"marketing_name\":\"9109 PA\","
        		+ "\"manufacturer\":\"Alcatel Radiotelephone (LAVAL)\",\"access_capability\":\"GSM 900\","
        		+ "\"model\":\"9109 PA\",\"vendor_name\":\"Alcatel Radiotelephone (LAVAL)\","
        		+ "\"user_equipment_type\":null,\"operating_system\":null,\"input_mode\":null},"
        		+ "\"mcc_mnc\":{\"mcc\":310,\"mnc\":560,\"country\":\"United States of America\","
        		+ "\"operator\":\"AT&T Mobility\"},\"cell_id\":4,\"duration\":1000,"
        		+ "\"ne_version\":\"11b\",\"imsi\":310560000000012,\"hier3_id\":4809532081614990000,"
        		+ "\"hier32_id\":8226896360947470000,\"hier321_id\":1150444940909480000},"
        		+ "{\"report_id\":5,\"date_time\":1357924560000,"
        		+ "\"event_cause\":{\"cause_code\":11,\"event_id\":4106,"
        		+ "\"description\":\"INITIAL CTXT SETUP-TRANSPORT REJECT\"},"
        		+ "\"failure_class\":{\"failure_class\":1,\"description\":\"HIGH PRIORITY ACCESS\"},"
        		+ "\"user_equipment\":{\"tac\":33000153,\"marketing_name\":\"9109 PA\","
        		+ "\"manufacturer\":\"Alcatel Radiotelephone (LAVAL)\","
        		+ "\"access_capability\":\"GSM 900\",\"model\":\"9109 PA\","
        		+ "\"vendor_name\":\"Alcatel Radiotelephone (LAVAL)\","
        		+ "\"user_equipment_type\":null,\"operating_system\":null,\"input_mode\":null},"
        		+ "\"mcc_mnc\":{\"mcc\":310,\"mnc\":560,\"country\":\"United States of America\","
        		+ "\"operator\":\"AT&T Mobility\"},\"cell_id\":4,\"duration\":1000,\"ne_version\":"
        		+ "\"11b\",\"imsi\":310560000000012,\"hier3_id\":4809532081614990000,"
        		+ "\"hier32_id\":8226896360947470000,\"hier321_id\":1150444940909480000}]");
	}
	
	@Test 
	@InSequence(1)
	public void nonexistentTACCodeTest() throws Exception {
		request = new ClientRequest(deploymentUrl.toString() + RESOURCE_PREFIX 
				+ "/unique_model_failures/404"); 
		// we're expecting a String back
        ClientResponse<String> responseObj = request.get(String.class);
        assertEquals(404, responseObj.getStatus());
        String response = responseObj.getEntity().trim();
        System.out.println("The to the second request is: " + response);
	}
}
