package com.excelreader.test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.User_equipment;
import com.ait.reader.ReadDataSetIntoMainMemory;

import java.util.ArrayList;
import java.util.List;

public class TestReadDataSetIntoMainMemory {

	String fileName;
	static NetworkEntity[] networkEntity;

	@Test
	public void testArrayListPopulated() throws InvalidFormatException, IOException {
		fileName = "C:\\Users\\Garrett\\Documents\\AIT Group Project - Sample Dataset.xls";
		ArrayList<NetworkEntity[]> arrayListOfNetworkEntities = new ArrayList<>();
		for (int i = 4; i >= 1; i--) {
		networkEntity = ReadDataSetIntoMainMemory.readFileInFromHardDrive(fileName, i);
		arrayListOfNetworkEntities.add(networkEntity);
		}
		assertEquals(arrayListOfNetworkEntities.size(), 4);
	}

	@Test(expected = Exception.class)
	public void testWorkBookThrows() throws InvalidFormatException, IOException {
		networkEntity = ReadDataSetIntoMainMemory.readFileInFromHardDrive("WrongFileName", 1);
	}

}
