package com.errorlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class DatabaseErrorLogger extends DateManipulator {

	private static int dailyErrorCount;
	private static int overallErrorCount;
	private static Date currentErrorLoggingDay = new Date();
	private String errorLoggerName;

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
			dailyErrorCount = 0;
		}else{
			dailyErrorCount = readErrorCountsFromFile("daily_error_count.txt");
		}
		overallErrorCount = readErrorCountsFromFile("overall_error_count.txt");
		
		increaseDailyErrorCount();
		increaseOverallErrorCount();
		WriteErrorsToFile.addErrorToFile(error, errorBuilder, workingFileName, fileErrorCount);
	}

	public String getErrorLoggerName() {
		return errorLoggerName;
	}

	private static void increaseDailyErrorCount() {
		dailyErrorCount++;
	}

	private static void increaseOverallErrorCount() {
		overallErrorCount++;
	}

	public static int getDailyErrorCount() {
		return dailyErrorCount;
	}

	public static int getOverallErrorCount() {
		return overallErrorCount;
	}

	 static void editErrorCountTextFiles(String fileName, int newErrorCount) {
		dailyErrorCount = 0;
		File dailyErrors = new File(fileName);
		try {
			BufferedWriter dailyErrorResetter = new BufferedWriter(new FileWriter(dailyErrors));
			PrintWriter errorOutput = new PrintWriter(dailyErrorResetter);
			errorOutput.write(newErrorCount);
			errorOutput.flush();
			errorOutput.close();
		} catch (IOException e) {
			System.out.println("Error writing to file...");
			e.printStackTrace();
		}
	}
	
	private static int readErrorCountsFromFile(String fileName){
		Scanner errorFileScanner = new Scanner(fileName);
		return errorFileScanner.nextInt();
	}
}
