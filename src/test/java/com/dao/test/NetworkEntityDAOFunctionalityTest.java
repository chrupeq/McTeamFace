package com.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.User_equipment;
import com.ait.db.rest.JaxRsActivator;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
public class NetworkEntityDAOFunctionalityTest {
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Base_data.class.getPackage())
				.addPackage(NetworkEntityDAO.class.getPackage())
				.addClass(IMSIStats.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	NetworkEntityDAO networkEntityDAO;
	
	
	@Test
//	@ApplyScriptBefore("resources/import.sql")
	public void testBaseDataNetworkEntityDAO() {
		List<? extends NetworkEntity> baseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA);
		assertFalse(baseDataList.isEmpty());
		assertEquals(10, baseDataList.size());
		// update
		Base_data baseData = (Base_data) networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).get(0);
		assertEquals(4, baseData.getCell_id());
		baseData.setCell_id(6);
		networkEntityDAO.updateNetworkEntity(baseData);
		// get by id
		baseData = (Base_data) networkEntityDAO.getNetworkEntityById(NetworkEntityType.BASE_DATA, 1);
		assertNotNull(baseData);
		assertEquals(1, baseData.getReport_id());
		System.out.println("The date from the DB: " + baseData.getDate_time().toString());
		assertEquals(6, baseData.getCell_id());
		// delete 
		networkEntityDAO.deleteNetworkEntity(baseData);
		assertEquals(9, networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size());
	}
	@Test
	public void testMccMncNetworkEntity() {
		List<? extends NetworkEntity> mccMncList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.MCC_MNC);
		assertFalse(mccMncList.isEmpty());
		assertEquals(5, mccMncList.size());
		// update based on the id
		assertNotNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.MCC_MNC, 238, 1));
		Mcc_mnc mccMnc = (Mcc_mnc) networkEntityDAO.getNetworkEntityById(NetworkEntityType.MCC_MNC, 238, 1);
		assertEquals(238, mccMnc.getMcc());
		assertEquals(1, mccMnc.getMnc());
		//update
		assertEquals("Denmark", mccMnc.getCountry());
		mccMnc.setCountry("Japan");
		networkEntityDAO.updateNetworkEntity(mccMnc);
		mccMnc = (Mcc_mnc) networkEntityDAO.getNetworkEntityById(NetworkEntityType.MCC_MNC, 238, 1);
		assertEquals("Japan", mccMnc.getCountry());
		// delete 
		mccMnc = (Mcc_mnc) networkEntityDAO.getNetworkEntityById(NetworkEntityType.MCC_MNC, 238, 2);
		assertNotNull(mccMnc);
		networkEntityDAO.deleteNetworkEntity(mccMnc);
		assertNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.MCC_MNC, 238, 2));
	}
	@Test
	public void testEventCauseNetworkEntity() {
		List<? extends NetworkEntity> eventCauseList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.EVENT_CAUSE);
		assertFalse(eventCauseList.isEmpty());
		assertEquals(8, eventCauseList.size());
		// update based on id
		assertNotNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.EVENT_CAUSE, 0, 4097));
		Event_cause eventCause = (Event_cause) networkEntityDAO.getNetworkEntityById(NetworkEntityType.EVENT_CAUSE, 0, 4097);
		assertEquals(new Integer(0), eventCause.getCause_code());
		assertEquals(new Integer(4097), eventCause.getEvent_id());
		// update
		assertEquals("RRC CONN SETUP-SUCCESS", eventCause.getDescription());
		eventCause.setDescription("CONNECTION FAILURE");
		networkEntityDAO.updateNetworkEntity(eventCause);
		eventCause = (Event_cause) networkEntityDAO.getNetworkEntityById(NetworkEntityType.EVENT_CAUSE, 0, 4097);
		assertEquals("CONNECTION FAILURE", eventCause.getDescription());
		// delete 
		eventCause = (Event_cause) networkEntityDAO.getNetworkEntityById(NetworkEntityType.EVENT_CAUSE, 1, 4097);
		assertNotNull(eventCause);
		networkEntityDAO.deleteNetworkEntity(eventCause);
		assertNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.EVENT_CAUSE, 1, 4097));
	}
	@Test
	public void testFailureClassEntity() {
		List<? extends NetworkEntity> failureClassList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.FAILURE_CLASS);
		assertFalse(failureClassList.isEmpty());
		assertEquals(3, failureClassList.size());
		// update based on id
		assertNotNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.FAILURE_CLASS, 0));
		Failure_class failureClass = (Failure_class) networkEntityDAO.getNetworkEntityById(NetworkEntityType.FAILURE_CLASS, 0);
		assertEquals(0, failureClass.getFailure_class());
		// update
		assertEquals("EMERGENCY", failureClass.getDescription());
		failureClass.setDescription("NOT SO BAD");
		networkEntityDAO.updateNetworkEntity(failureClass);
		failureClass = (Failure_class) networkEntityDAO.getNetworkEntityById(NetworkEntityType.FAILURE_CLASS, 6);
		assertEquals("NUCLEAR MELTDOWN", failureClass.getDescription());
		// delete 
		networkEntityDAO.deleteNetworkEntity(failureClass);
		assertNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.FAILURE_CLASS, 6));
	}
	@Test
	public void testUserEquipmentNetworkEntity() {
		List<? extends NetworkEntity> userEquipment = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.USER_EQUIPMENT);
		assertNotNull(userEquipment);
		assertEquals(5, userEquipment.size());
		// update based on id
		assertNotNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.USER_EQUIPMENT, 100200));
		User_equipment userEquipmentReference = (User_equipment) networkEntityDAO.getNetworkEntityById(NetworkEntityType.USER_EQUIPMENT, 100200);
		assertEquals(100200, userEquipmentReference.getTac());
		assertEquals("Siemens", userEquipmentReference.getManufacturer());
		userEquipmentReference.setManufacturer("Mitsubishi");
		networkEntityDAO.updateNetworkEntity(userEquipmentReference);
		userEquipmentReference = (User_equipment) networkEntityDAO.getNetworkEntityById(NetworkEntityType.USER_EQUIPMENT, 100200);
		assertEquals("Mitsubishi", userEquipmentReference.getManufacturer());
		// delete
		networkEntityDAO.deleteNetworkEntity(userEquipmentReference);
		assertNull(networkEntityDAO.getNetworkEntityById(NetworkEntityType.USER_EQUIPMENT, 100200));
	}
	@Test
	public void testTheEnumChecker() {
		NetworkEntity baseData = new Base_data();
		assertEquals(NetworkEntityType.BASE_DATA, networkEntityDAO.getNetworkEntityType(baseData));
		NetworkEntity mccMnc = new Mcc_mnc();
		assertEquals(NetworkEntityType.MCC_MNC, networkEntityDAO.getNetworkEntityType(mccMnc));
		NetworkEntity eventCause = new Event_cause();
		assertEquals(NetworkEntityType.EVENT_CAUSE, networkEntityDAO.getNetworkEntityType(eventCause));
		NetworkEntity failureClass = new Failure_class();
		assertEquals(NetworkEntityType.FAILURE_CLASS, networkEntityDAO.getNetworkEntityType(failureClass));
		NetworkEntity userEquipment = new User_equipment();
		assertEquals(NetworkEntityType.USER_EQUIPMENT, networkEntityDAO.getNetworkEntityType(userEquipment));
	}
}
