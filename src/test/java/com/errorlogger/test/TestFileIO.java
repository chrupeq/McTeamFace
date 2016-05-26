package com.errorlogger.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.text.DateFormatter;

import org.junit.Before;
import org.junit.Test;

import com.errorlogger.DatabaseErrorLogger;

public class TestFileIO {

	final String DIVIDER = "********************";
	final String BEFORE_ERROR = "ERROR: Erroneous or null data found at column: ";
	DateFormat dateFormatter;
	Date todaysDate;
	File testFile;
	Scanner fileReader;
	
	
	DatabaseErrorLogger errorLogger;
	
	
	@Before
	public void setUp() throws FileNotFoundException{
		 errorLogger = new DatabaseErrorLogger("errorLogger");
		 testFile = new File("testFile.txt");
		 fileReader = new Scanner(testFile);
		 dateFormatter = new SimpleDateFormat("dd-MM-yy");
	}
	
	@Test
	public void testFileCreated() {
		errorLogger.errorFound("test error", 2, "testFile.txt");
		assertTrue(testFile.exists());
		testFile.delete();
	}
	
	@Test
	public void testFileInputsCorrectly(){
		errorLogger.errorFound("test error", 2, "testFile.txt");
		assertEquals("testError", fileReader.nextLine());
		assertEquals(BEFORE_ERROR + 2, fileReader.nextLine());
		assertEquals(dateFormatter.format(new Date()), fileReader.nextLine());
		assertEquals(DIVIDER, fileReader.nextLine());
	}

}
