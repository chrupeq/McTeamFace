package com.errorlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class WriteErrorsToFile extends DateManipulator{

	private static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
	DatabaseErrorLogger errorLogger = new DatabaseErrorLogger("writehelper");
	
	static void addErrorToFile(String error, StringBuilder errorString, String workingFileName,
			int fileErrorCount) {
		String todaysDate = dateFormatter.format(getCurrentDate());

		File errorLog = new File("C:\\Users\\A00226084\\Desktop\\" + workingFileName + " " + todaysDate + ".txt");

		if (errorLog.exists()) {
			try {
				PrintWriter errorWriter = new PrintWriter(new BufferedWriter(new FileWriter(errorLog, true)));
				errorWriter.write(errorString.toString());
				errorWriter.write("Error count for this file: " + fileErrorCount);
				errorWriter.close();
				DatabaseErrorLogger.editErrorCountTextFiles("daily_error_count.txt", fileErrorCount + DatabaseErrorLogger.getDailyErrorCount());
				DatabaseErrorLogger.editErrorCountTextFiles("overall_error_count.txt", DatabaseErrorLogger.getOverallErrorCount() + fileErrorCount);
			} catch (IOException e) {
				System.out.println("Error writing to file.");
				e.printStackTrace();
			}
		} else {
			try {
				PrintWriter errorWriter = new PrintWriter(new BufferedWriter(new FileWriter(errorLog, false)));
				errorWriter.write(errorString.toString());
				errorWriter.write("Error count for this file: " + fileErrorCount);
				errorWriter.close();
				DatabaseErrorLogger.editErrorCountTextFiles("daily_error_count.txt", fileErrorCount + DatabaseErrorLogger.getDailyErrorCount());
				DatabaseErrorLogger.editErrorCountTextFiles("overall_error_count.txt", DatabaseErrorLogger.getOverallErrorCount() + fileErrorCount);
			} catch (IOException e) {
				System.out.println("Error writing to file.");
				e.printStackTrace();
			}
		}
	}
}
