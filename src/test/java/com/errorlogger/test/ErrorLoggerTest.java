package com.errorlogger.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner; 


import com.errorlogger.DatabaseErrorLogger;
import com.errorlogger.DateManipulator;

@RunWith(PowerMockRunner.class)
public class ErrorLoggerTest {
	
	private static int counter;
	
	private Scanner dailyErrorScanner;
	private Scanner overallErrorScanner;
	private DatabaseErrorLogger databaseErrorLogger;
	private StringBuilder errorBuilder;
	File dailyErrorCount = new File("daily_error_count.txt");
	File overallErrorCount = new File("overall_error_count.txt");
	private String errorFile;
	private SimpleDateFormat dateFormatter;
	
	Method editErrorCountTextFiles;
	Method checkNewDay;
	Method returnLastErrorLoggingDate;
	Method readErrorCountsFromFile;
	
	private final String FILENAME = "test_filename";
	private final String TEST_ERROR = "test error";
	private final String DAILY_ERRORS = "daily_error_count.txt";
	private final String OVERALL_ERRORS = "overall_error_count.txt";
	private final Path DAILY_ERRORS_FILE = Paths.get(DAILY_ERRORS);
	private final Path OVERALL_ERRORS_FILE = Paths.get(OVERALL_ERRORS);
	
	@Before
	public void setUp() throws IOException, NoSuchFieldException, SecurityException, NoSuchMethodException, InterruptedException{
		dateFormatter = new SimpleDateFormat("dd-MM-yy");
		databaseErrorLogger = new DatabaseErrorLogger("Test Logger");
		errorBuilder = new StringBuilder("another test error\r\n");
		editErrorCountTextFiles = DatabaseErrorLogger.class.getDeclaredMethod("editErrorCountTextFiles", String.class, int.class);
		checkNewDay = DateManipulator.class.getDeclaredMethod("checkNewDay", Date.class, Date.class);
		returnLastErrorLoggingDate = DateManipulator.class.getDeclaredMethod("returnLastErrorLoggingDate");
		readErrorCountsFromFile = DatabaseErrorLogger.class.getDeclaredMethod("readErrorCountsFromFile", String.class);
		editErrorCountTextFiles.setAccessible(true);
		checkNewDay.setAccessible(true);
		returnLastErrorLoggingDate.setAccessible(true);
		readErrorCountsFromFile.setAccessible(true);
		
		errorFile = "test_filename " + dateFormatter.format(DateManipulator.getCurrentDate()) + ".txt";
		
		if(dailyErrorCount.exists()){
			Files.delete(DAILY_ERRORS_FILE);
		}
		
		if(overallErrorCount.exists() && counter < 1){
			counter ++;
			Files.delete(OVERALL_ERRORS_FILE);
		}
	}
	
	@Test
	public void testThatBothDailyAndOverallErrorsAreRecordedOnce() throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
	int overAllErrors = 0;
		try{
	overAllErrors = (Integer) readErrorCountsFromFile.invoke(DatabaseErrorLogger.class, "overall_error_count.txt");
	}catch(Exception e){
		System.out.println("File not found");
	}
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 12);
		dailyErrorScanner = new Scanner(new File("daily_error_count.txt"));
		overallErrorScanner = new Scanner(new File("overall_error_count.txt"));
		assertEquals(12, dailyErrorScanner.nextInt());
		assertEquals(overAllErrors + 12, overallErrorScanner.nextInt());
		dailyErrorScanner.close();
		overallErrorScanner.close();
		editErrorCountTextFiles.invoke(DatabaseErrorLogger.class, "daily_error_count.txt", 0);
		
	}
	
	@Test
	public void testThatBothDailyAndOverallErrorsIncrease() throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		int overAllErrors = 0;
		try{
		overAllErrors = (Integer) readErrorCountsFromFile.invoke(DatabaseErrorLogger.class, "overall_error_count.txt");
		}catch(Exception e){
			System.out.println("File not found.");
		}
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 12);
		dailyErrorScanner = new Scanner(new File("daily_error_count.txt"));
		overallErrorScanner = new Scanner(new File("overall_error_count.txt"));
		assertEquals(12, dailyErrorScanner.nextInt());
		assertEquals(12, overallErrorScanner.nextInt());
		dailyErrorScanner.close();
		overallErrorScanner.close();
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 12);
		dailyErrorScanner = new Scanner(new File("daily_error_count.txt"));
		overallErrorScanner = new Scanner(new File("overall_error_count.txt"));
		assertEquals(24, dailyErrorScanner.nextInt());
		assertEquals(24, overallErrorScanner.nextInt());
		dailyErrorScanner.close();
		overallErrorScanner.close();
		editErrorCountTextFiles.invoke(DatabaseErrorLogger.class, "daily_error_count.txt", 0);

	}
	
	@PrepareForTest(DateManipulator.class)
	@Test
	public void testThatDailyErrorCountResetsAtEndOfDay() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException{
		PowerMockito.mockStatic(DateManipulator.class);
		PowerMockito.when(DateManipulator.getCurrentDate()).thenReturn(new Date());
		int overAllErrors = (Integer) readErrorCountsFromFile.invoke(DatabaseErrorLogger.class, "overall_error_count.txt");
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 12);
		dailyErrorScanner = new Scanner(new File("daily_error_count.txt"));
		overallErrorScanner = new Scanner(new File("overall_error_count.txt"));
		assertEquals(12, dailyErrorScanner.nextInt());
		assertEquals(overAllErrors + 12, overallErrorScanner.nextInt());
		PowerMockito.when(DateManipulator.checkNewDay((Date) returnLastErrorLoggingDate.invoke(DateManipulator.class), new Date())).thenReturn(true);
		dailyErrorScanner.close();
		overallErrorScanner.close();
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 12);
		dailyErrorScanner = new Scanner(new File("daily_error_count.txt"));
		assertEquals(12, dailyErrorScanner.nextInt());
		editErrorCountTextFiles.invoke(DatabaseErrorLogger.class, "daily_error_count.txt", 0);	
	}
	
	@Test
	public void testThatDatesInTheFutureAreCaught() throws ParseException{
		assertFalse(DateManipulator.checkThatDateIsNotInTheFuture("12/12/2016 16:21"));
		assertFalse(DateManipulator.checkThatDateIsNotInTheFuture("16/09/2016 15:42"));
	}
	
	@Test
	public void testThatDatesInThePastAreNotCaught() throws ParseException{
		assertTrue(DateManipulator.checkThatDateIsNotInTheFuture("06/06/2016 14:41"));
		assertTrue(DateManipulator.checkThatDateIsNotInTheFuture("12/01/2015 16:32"));
	}
	
	@Test
	public void testThatErrorDocumentContainsOneLineWhenSingleErrorLogged() throws IOException{
		Path errorFilePath = Paths.get(errorFile);
		File errorFileLocation = new File(errorFile);
		if(errorFileLocation.exists()){
			Files.delete(errorFilePath);
		}
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 1);
		counter = 0;
		Scanner errorFileReader = new Scanner(new File(errorFile));
		while(errorFileReader.hasNext()){
			counter ++;
			errorFileReader.nextLine();		
			}
		assertEquals(2, counter);
		errorFileReader.close();
	}
	
	@Test
	public void testThatErrorDocumentContainsFourLinesWhenTwoErrorsLogged() throws IOException{
		Path errorFilePath = Paths.get(errorFile);
		File errorFileLocation = new File(errorFile);
		if(errorFileLocation.exists()){
			Files.delete(errorFilePath);
		}
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 1);
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 1);
		counter = 0;
		Scanner errorFileReader = new Scanner(new File(errorFile));
		while(errorFileReader.hasNext()){
			counter ++;
			errorFileReader.nextLine();		
			}
		assertEquals(4, counter);
		errorFileReader.close();
	}

}
