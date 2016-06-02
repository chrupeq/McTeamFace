package com.excelreader.test;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import com.ait.project.ReadDataSetIntoMainMemory;
import java.util.ArrayList;

public class TestReadDataSetIntoMainMemory {

	String fileName;
	ArrayList<Object[][]> arrayListOfSheets;

	@Test
	public void testArrayPopulated() throws InvalidFormatException, IOException {
		fileName = "C:\\Users\\A00236958\\Documents\\AIT Group Project - Sample Dataset.xls";
		arrayListOfSheets = new ArrayList<Object[][]>();
		arrayListOfSheets = ReadDataSetIntoMainMemory.readFileInFromHardDrive(fileName);
		assertEquals(arrayListOfSheets.size(), 5);
	}

	@Test(expected = Exception.class)
	public void testWorkBookThrows() throws InvalidFormatException, IOException {
		arrayListOfSheets = ReadDataSetIntoMainMemory.readFileInFromHardDrive("WrongFileName");
	}

}
