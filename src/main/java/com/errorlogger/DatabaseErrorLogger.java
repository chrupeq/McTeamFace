package com.errorlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseErrorLogger extends DateManipulator {

	private static int dailyErrorCount;
	private static int overallErrorCount;
//	final private static String NEW_LINE = "\r\n";
	final private static String DIVIDER = "********************";
	private static Date currentErrorLoggingDay = new Date();
	private String errorLoggerName;
	private static DateFormat dateAndTimeFormatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
	private static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");

	public DatabaseErrorLogger(String errorLoggerName) {

		this.errorLoggerName = errorLoggerName;

	}

	/*
	 * Method that is called when an error is detected Increases both daily and
	 * overall error count by calling relevant methods Also checks
	 */

	public void errorsFound(String error, StringBuilder errorBuilder, String workingFileName) {

		if (!checkNewDay(currentErrorLoggingDay, new Date()))
			resetDailyErrorCount();

		increaseDailyErrorCount();
		increaseOverallErrorCount();
		addErrorToFile(error, errorBuilder, workingFileName);
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

	private static void addErrorToFile(String error, StringBuilder errorString, String workingFileName) {

		String todaysDate = dateFormatter.format(getCurrentDate());
//		String todaysDateAndTime = dateAndTimeFormatter.format(getCurrentDateAndTime());

		File errorLog = new File(workingFileName + " " + todaysDate + ".txt");

		if (errorLog.exists()) {
			try {
				PrintWriter errorWriter = new PrintWriter(new BufferedWriter(new FileWriter(errorLog, true)));
				errorWriter.write(errorString.toString());
				errorWriter.write("Error count for this file: " + dailyErrorCount);
				errorWriter.close();
			} catch (IOException e) {
				System.out.println("Error writing to file.");
				e.printStackTrace();
			}
		} else {
			try {
				PrintWriter errorWriter = new PrintWriter(new BufferedWriter(new FileWriter(errorLog, false)));
				errorWriter.write(errorString.toString());
				errorWriter.write("Error count for this file: " + dailyErrorCount);
				errorWriter.close();
			} catch (IOException e) {
				System.out.println("Error writing to file.");
				e.printStackTrace();
			}
		}
	}

	public static int getDailyErrorCount() {
		return dailyErrorCount;
	}

	public static int getOverallErrorCount() {
		return overallErrorCount;
	}

	private static void resetDailyErrorCount() {
		dailyErrorCount = 0;
	}
}
