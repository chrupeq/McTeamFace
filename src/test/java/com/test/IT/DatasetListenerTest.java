package com.test.IT;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.util.file.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.datasetListener.DatasetFileListener;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.NetworkEntity;
import com.fileuploader.ExcelFileUploader;
import com.google.common.io.FileWriteMode;
import com.google.gson.internal.Streams;
import com.sun.jna.platform.FileMonitor.FileEvent;
import com.sun.tools.internal.ws.wsdl.framework.Entity;

import static java.nio.file.StandardWatchEventKinds.*;

@RunWith(Arquillian.class)
public class DatasetListenerTest {
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Base_data.class.getPackage())
				.addPackage(NetworkEntityDAO.class.getPackage())
				.addClass(Files.class).addClass(org.jboss.util.stream.Streams.class)
				.addAsResource("META-INF/persistence.xml");
				/*.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("import.sql")
	            .addAsWebInfResource("jbossas-ds.xml")*/
	            //.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	//Need to provide path to jboss bin and datasets folder
	private static String jBossPathDatasetDir = "/opt/jboss-as-7.1.1.Final/bin/dataSets/";
	private static String jBossPathBinDir = "/opt/jboss-as-7.1.1.Final/bin/";

	@EJB
	NetworkEntityDAO networkEntityDAO;
	
	@Before
	public void init() throws Exception {
		
	}

	@Test
	public void testTxtFileCreatedAndNoChangesToDB() throws Exception {

		int baseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		File tempFile = new File(jBossPathDatasetDir, "testFile.txt");
		assertTrue("Could not create temp file", tempFile.createNewFile());
		File createdFile = new File(jBossPathDatasetDir+"testFile.txt");
		assertTrue("File is created", createdFile.exists());
		assertEquals(createdFile.getName(), "testFile.txt");
		int afterFileAddedBaseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		assertEquals(baseDataList, afterFileAddedBaseDataList);
		createdFile.delete();
	}

/*	@Test
	public void testEmptyXlsFileCreatedAndNoChangesToDB() throws Exception {

		int sizeBeforeFileAdded = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		File tempFile = new File(jBossPathDatasetDir, "testFile.xls");
		assertTrue("Could not create temp file", tempFile.createNewFile());
		File createdFile = new File(jBossPathDatasetDir+"testFile.xls");
		assertTrue("File is created", createdFile.exists());
		assertEquals(createdFile.getName(), "testFile.xls");
		assertEquals(sizeBeforeFileAdded, networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size());
		createdFile.delete();
	}*/
	
	@Test
	public void testOneXlsFileCreatedWithDataAndChangedMadeToDB() throws Exception {
		int sizeBeforeFileAdded = 0;
		try{
		sizeBeforeFileAdded = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		}catch(ArrayIndexOutOfBoundsException e){
		}
		File dataSetWithData = new File(jBossPathBinDir+"AITGroup Project - Dataset 3A.xls");
		File copyOfDataSetWithData = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3A.xls");
		Files.copy(dataSetWithData, copyOfDataSetWithData);
		File copiedDataset = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3A.xls");
		Thread.sleep(5000);
		assertEquals(copiedDataset.getName(), "AITGroup Project - Dataset 3A.xls");
		Thread.sleep(30000);
		try{
		int afterFileAddedBaseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		assertTrue(sizeBeforeFileAdded!=afterFileAddedBaseDataList);
		}catch(ArrayIndexOutOfBoundsException e){
		}
		copiedDataset.delete();
		Thread.sleep(2000);
	}
	
	@Test
	public void testTwoXlsFileCreatedWithDataAndChangedMadeToDB() throws Exception {

		int sizeBeforeFileAdded=0;
		try{
		sizeBeforeFileAdded = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		}catch(ArrayIndexOutOfBoundsException e){
		}
		File dataSetWithData = new File(jBossPathBinDir+"AITGroup Project - Dataset 3A.xls");
		File copyOfDataSet1WithData = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3A.xls");
		File copyOfDataSet2WithData = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3B.xls");
		Files.copy(dataSetWithData, copyOfDataSet1WithData);
		File copiedDataset1 = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3A.xls");
		Thread.sleep(5000);
		Files.copy(dataSetWithData, copyOfDataSet2WithData);
		File copiedDataset2 = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3B.xls");
		Thread.sleep(5000);
		assertEquals(copiedDataset1.getName(), "AITGroup Project - Dataset 3A.xls");
		assertEquals(copiedDataset2.getName(), "AITGroup Project - Dataset 3B.xls");
		Thread.sleep(50000);
/*		try{
		int afterFileAddedBaseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		assertTrue(sizeBeforeFileAdded!=afterFileAddedBaseDataList);
		}catch(ArrayIndexOutOfBoundsException e){
			
		}*/
		copiedDataset1.delete();
		copiedDataset2.delete();
		Thread.sleep(5000);
	}
	
	/*List<? extends NetworkEntity> baseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA);
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
	}*/
}
