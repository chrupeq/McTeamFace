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
import java.util.Date;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito; 
import org.powermock.api.mockito.PowerMockito; 
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
	
	Method editErrorCountTextFiles;
	Method checkNewDay;
	Method returnLastErrorLoggingDate;
	Method readErrorCountsFromFile;
	
	private DateManipulator dateManipulator;
	
	private final String FILENAME = "test_filename";
	private final String TEST_ERROR = "test error";
	private final String DAILY_ERRORS = "daily_error_count.txt";
	private final String OVERALL_ERRORS = "overall_error_count.txt";
	private final Path DAILY_ERRORS_FILE = Paths.get(DAILY_ERRORS);
	private final Path OVERALL_ERRORS_FILE = Paths.get(OVERALL_ERRORS);
	
	@Before
	public void setUp() throws IOException, NoSuchFieldException, SecurityException, NoSuchMethodException, InterruptedException{
		databaseErrorLogger = new DatabaseErrorLogger("Test Logger");
		errorBuilder = new StringBuilder("another test error");
		editErrorCountTextFiles = DatabaseErrorLogger.class.getDeclaredMethod("editErrorCountTextFiles", String.class, int.class);
		checkNewDay = DateManipulator.class.getDeclaredMethod("checkNewDay", Date.class, Date.class);
		returnLastErrorLoggingDate = DateManipulator.class.getDeclaredMethod("returnLastErrorLoggingDate");
		readErrorCountsFromFile = DatabaseErrorLogger.class.getDeclaredMethod("readErrorCountsFromFile", String.class);
		editErrorCountTextFiles.setAccessible(true);
		checkNewDay.setAccessible(true);
		returnLastErrorLoggingDate.setAccessible(true);
		readErrorCountsFromFile.setAccessible(true);
		
		dateManipulator = Mockito.mock(DateManipulator.class);
		
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
		
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 12);
		dailyErrorScanner = new Scanner(new File("daily_error_count.txt"));
		overallErrorScanner = new Scanner(new File("overall_error_count.txt"));
		assertEquals(12, dailyErrorScanner.nextInt());
		assertEquals(12, overallErrorScanner.nextInt());
		dailyErrorScanner.close();
		overallErrorScanner.close();
		editErrorCountTextFiles.invoke(DatabaseErrorLogger.class, "daily_error_count.txt", 0);
		
	}
	
	@Test
	public void testThatBothDailyAndOverallErrorsIncrease() throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
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
	
	@Test
	public void testThatDailyErrorCountResetsAtEndOfDay() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException{
		int overAllErrors = (Integer) readErrorCountsFromFile.invoke(DatabaseErrorLogger.class, "overall_error_count.txt");
		databaseErrorLogger.errorsFound(TEST_ERROR, errorBuilder, FILENAME, 12);
		dailyErrorScanner = new Scanner(new File("daily_error_count.txt"));
		overallErrorScanner = new Scanner(new File("overall_error_count.txt"));
		assertEquals(12, dailyErrorScanner.nextInt());
		assertEquals(overAllErrors + 12, overallErrorScanner.nextInt());
		PowerMockito.when(checkNewDay.invoke(DateManipulator.class, (Date) returnLastErrorLoggingDate.invoke(DateManipulator.class), new Date())).thenReturn(true);
		dailyErrorScanner.close();
		overallErrorScanner.close();
		editErrorCountTextFiles.invoke(DatabaseErrorLogger.class, "daily_error_count.txt", 0);
		
	}

}
