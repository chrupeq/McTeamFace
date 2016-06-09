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

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Base_data;
import com.ait.reader.ReadDataSetIntoMainMemory;
import com.validation.SendValidatedInfoToDB;

@RunWith(Arquillian.class)
public class SendValidatedInfoToDBtest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Base_data.class.getPackage())
				.addPackage(NetworkEntityDAO.class.getPackage())
				.addPackage(ReadDataSetIntoMainMemory.class.getPackage())
				.addPackage(SendValidatedInfoToDB.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
	            .addAsWebInfResource("jbossas-ds.xml")
	            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	
	@Test
	public void testSendValidatedInfoToDBtest() {
		SendValidatedInfoToDB sendValidatedInfoToDB = new SendValidatedInfoToDB();
		String fileName = "C:\\Users\\A00226084\\Downloads\\AIT Group Project - Sample Dataset.xls";
		try {
			sendValidatedInfoToDB.sendData(ReadDataSetIntoMainMemory.readFileInFromHardDrive(fileName).get(1), 1);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
