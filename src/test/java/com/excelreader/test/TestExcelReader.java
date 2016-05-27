package com.excelreader.test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.ait.project.ReadDataSetIntoMainMemory;
import java.util.ArrayList;

public class TestExcelReader{
	
	String fileName;
	ArrayList<Object[][]> arrays;	
	
//	@Before
//	public void setUp() throws Exception{
//		fileName = "C:\\Users\\A00236958\\Documents\\AIT Group Project - Sample Dataset.xls";
//		arrays = new ArrayList<Object[][]>();
//		arrays = SimpleExcelReaderExample.readFileInFromHardDrive(fileName);		
//	}
	
	@Test
	public void testArrayPopulated() throws InvalidFormatException, IOException {
		fileName = "C:\\Users\\A00236958\\Documents\\AIT Group Project - Sample Dataset.xls";
		arrays = new ArrayList<Object[][]>();
		arrays = ReadDataSetIntoMainMemory.readFileInFromHardDrive(fileName);	
		assertEquals(arrays.size(),5);
	}
	
	@Test(expected = Exception.class)
	public void testWorkBookCatch() throws InvalidFormatException, IOException{
		arrays = ReadDataSetIntoMainMemory.readFileInFromHardDrive("WrongFileName");
	}
	
//	@After
//    public void tearDown() throws IOException {
//		//FileInputStream fileIn = new FileInputStream("C:\\Users\\A00236958\\Documents\\xssf-align.xlsx");
//        String fileName = "C:\\Users\\A00236958\\Documents\\xssf-align.xlsx";
//		Path path = Paths.get(fileName);
//		Files.deleteIfExists(path);
//    }

}
