package com.dao.test;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.CompositePrimaryKeyType;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityDAO.NetworkEntityTypeEnumFactory;
import com.ait.db.data.NetworkEntityDAO.PrimaryKeyFactory;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.CompositePK;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Event_causeKey;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.Mcc_mncKey;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.User_equipment;
import com.ait.reader.ReadDataSetIntoMainMemory;
import com.errorlogger.DatabaseErrorLogger;
import com.errorlogger.DateManipulator;
import com.errorlogger.WriteErrorsToFile;
import com.fileuploader.ExcelFileUploader;
import com.validation.DataValidator;
import com.validation.JDBCConnectionManager;
import com.validation.SendValidatedInfoToDB;
import com.validation.ValidationDataFromJDBC;

@RunWith(Arquillian.class)
public class SendValidatedInfoToDBtest {

	@Deployment
	public static Archive<?> createDeployment() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Base_data.class.getPackage())
				.addPackage(NetworkEntityDAO.class.getPackage())
				.addPackage(ReadDataSetIntoMainMemory.class.getPackage())
				.addPackage(SendValidatedInfoToDB.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		archive.addClasses(CompositePrimaryKeyType.class, NetworkEntityDAO.class, NetworkEntityType.class);
		archive.addClasses(PrimaryKeyFactory.class, NetworkEntityTypeEnumFactory.class);
		archive.addClasses(User_equipment.class, Base_data.class, Event_cause.class, Event_causeKey.class,
				Mcc_mncKey.class, Failure_class.class, CompositePK.class, NetworkEntity.class, Mcc_mnc.class);
		archive.addClass(ReadDataSetIntoMainMemory.class);
		archive.addClasses(DatabaseErrorLogger.class, DateManipulator.class, WriteErrorsToFile.class);
		archive.addClass(ExcelFileUploader.class);
		archive.addClasses(ValidationDataFromJDBC.class, JDBCConnectionManager.class, SendValidatedInfoToDB.class,
				DataValidator.class);
		return archive;
	}
	
	
	@Test
	public void testSendValidatedInfoToDBtest() {
		SendValidatedInfoToDB sendValidatedInfoToDB = new SendValidatedInfoToDB();
		String fileName = "C:\\Users\\A00226084\\Downloads\\AIT Group Project - Sample Dataset.xls";
		try {
			sendValidatedInfoToDB.sendData(ReadDataSetIntoMainMemory.readFileInFromHardDrive(fileName).get(1), 1);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
