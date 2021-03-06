package com.errorlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fileuploader.ExcelFileUploader;

public class WriteErrorsToFile extends DateManipulator{

	private static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
	DatabaseErrorLogger errorLogger = new DatabaseErrorLogger("writehelper");
	
	static void addErrorToFile(String error, StringBuilder errorString, String workingFileName,
			int fileErrorCount) {
		String todaysDate = dateFormatter.format(getCurrentDate());

		File errorLog = new File(workingFileName + " " + todaysDate + ".txt");

		if (errorLog.exists()) {
			try {
				PrintWriter errorWriter = new PrintWriter(new BufferedWriter(new FileWriter(errorLog, true)));
				errorWriter.write(errorString.toString());
				errorWriter.write("Error count for this file: " + fileErrorCount);
				errorWriter.close();
				DatabaseErrorLogger.editErrorCountTextFiles("daily_error_count.txt", fileErrorCount + DatabaseErrorLogger.getDailyErrorCount());
				DatabaseErrorLogger.editErrorCountTextFiles("overall_error_count.txt", DatabaseErrorLogger.getOverallErrorCount() + fileErrorCount);
				ExcelFileUploader.setProgressVariable(75);
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
				ExcelFileUploader.setProgressVariable(80);
			} catch (IOException e) {
				System.out.println("Error writing to file.");
				e.printStackTrace();
			}
		}
	}
}
