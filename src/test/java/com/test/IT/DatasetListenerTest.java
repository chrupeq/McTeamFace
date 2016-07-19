package com.test.IT;

import static org.junit.Assert.*;

import java.io.File;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.util.file.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ait.db.data.IMSIDAO;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.imsiStats.IMSIStats;

@RunWith(Arquillian.class)
public class DatasetListenerTest {
	
	@Deployment
	public static Archive<?> createDeployment() {
		

		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Base_data.class.getPackage())
				.addPackage(NetworkEntityDAO.class.getPackage())
				.addPackage(IMSIDAO.class.getPackage())
				.addPackage(IMSIStats.class.getPackage())
				.addClass(Files.class).addClass(org.jboss.util.stream.Streams.class)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				//.addAsLibraries(files)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
				/*.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
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
	@InSequence(1)
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

	@Test
	@InSequence(4)
	public void testEmptyXlsFileCreatedAndNoChangesToDB() throws Exception {

		int sizeBeforeFileAdded = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		File dataSetWithData = new File(jBossPathBinDir+"emptySet.xls");
		File copyOfDataSetWithData = new File(jBossPathDatasetDir+"emptySet.xls");
		Files.copy(dataSetWithData, copyOfDataSetWithData);
		File copiedDataset = new File(jBossPathDatasetDir+"emptySet.xls");
		Thread.sleep(5000);
		assertEquals(copiedDataset.getName(), "emptySet.xls");
		Thread.sleep(5000);
		try{
		int afterFileAddedBaseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		assertTrue(sizeBeforeFileAdded==afterFileAddedBaseDataList);
		}catch(ArrayIndexOutOfBoundsException e){
		}
		copiedDataset.delete();
		Thread.sleep(2000);
	}
	
	@Test
	@InSequence(2)
	public void testOneXlsFileCreatedWithDataAndChangedMadeToDB() throws Exception {
		int sizeBeforeFileAdded = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
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
	@InSequence(3)
	public void testTwoXlsFileCreatedWithDataAndChangedMadeToDB() throws Exception {

		int sizeBeforeFileAdded=networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		try{
		sizeBeforeFileAdded = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		}catch(ArrayIndexOutOfBoundsException e){
		}
		File dataSetWithData = new File(jBossPathBinDir+"AITGroup Project - Dataset 3A.xls");
		File dataSetWithData2 = new File(jBossPathBinDir+"AIT Group Project - Dataset 3B.xls");
		File copyOfDataSet1WithData = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3A.xls");
		File copyOfDataSet2WithData = new File(jBossPathDatasetDir+"AIT Group Project - Dataset 3B.xls");
		Files.copy(dataSetWithData, copyOfDataSet1WithData);
		File copiedDataset1 = new File(jBossPathDatasetDir+"AITGroup Project - Dataset 3A.xls");
		Thread.sleep(5000);
		Files.copy(dataSetWithData2, copyOfDataSet2WithData);
		File copiedDataset2 = new File(jBossPathDatasetDir+"AIT Group Project - Dataset 3B.xls");
		Thread.sleep(5000);
		assertEquals(copiedDataset1.getName(), "AITGroup Project - Dataset 3A.xls");
		assertEquals(copiedDataset2.getName(), "AIT Group Project - Dataset 3B.xls");
		Thread.sleep(30000);
		try{
		int afterFileAddedBaseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA).size();
		assertTrue(sizeBeforeFileAdded!=afterFileAddedBaseDataList);
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		copiedDataset1.delete();
		Thread.sleep(5000);
		copiedDataset2.delete();
		Thread.sleep(5000);
	}

}
