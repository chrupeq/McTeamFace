package com.errorlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DatabaseErrorLogger extends DateManipulator {

	private static int dailyErrorCount;
	private static int overallErrorCount;
	private static Date currentErrorLoggingDay = DateManipulator.returnLastErrorLoggingDate();
	private String errorLoggerName;
	File dailyErrorCountRecord = new File("daily_error_count.txt");
	File overallErrorCountRecord = new File("overall_error_count.txt");

	public DatabaseErrorLogger(String errorLoggerName) {

		this.errorLoggerName = errorLoggerName;

	}

	/**
	 * Method that is called when an error is detected. Increases both daily and
	 * overall error count by calling relevant methods Also checks if a new day has begun
	 * and resets error count based on that.
	 */

	public void errorsFound(String error, StringBuilder errorBuilder, String workingFileName, int fileErrorCount) {
		
		if (!checkNewDay(currentErrorLoggingDay, new Date())){
			editErrorCountTextFiles("daily_error_count.txt", 0);
			writeTodaysDateToErrorFile();
			dailyErrorCount = 0;
		}else{
			if(dailyErrorCountRecord.exists()){
			dailyErrorCount = readErrorCountsFromFile("daily_error_count.txt");
			}
		}
		if(overallErrorCountRecord.exists()){
		overallErrorCount = readErrorCountsFromFile("overall_error_count.txt");
		}
		WriteErrorsToFile.addErrorToFile(error, errorBuilder, workingFileName, fileErrorCount);
	}

	public String getErrorLoggerName() {
		return errorLoggerName;
	}

	public static int getDailyErrorCount() {
		return dailyErrorCount;
	}

	public static int getOverallErrorCount() {
		return overallErrorCount;
	}

	 static void editErrorCountTextFiles(String fileName, int newErrorCount) {
		dailyErrorCount = 0;
		File errorRecord = new File(fileName);
		try {
			BufferedWriter dailyErrorResetter = new BufferedWriter(new FileWriter(errorRecord, false));
			PrintWriter errorOutput = new PrintWriter(dailyErrorResetter);
			errorOutput.write(String.valueOf(newErrorCount));
			errorOutput.close();
		} catch (IOException e) {
			System.out.println("Error writing to file...");
			e.printStackTrace();
		}
	}
	
	private static int readErrorCountsFromFile(String fileName){
		Scanner errorFileScanner;
		try {
			errorFileScanner = new Scanner(new File(fileName));
			int errorNumber = errorFileScanner.nextInt();
			errorFileScanner.close();
			return errorNumber;
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		}
		return 0;
	}
}
