package com.errorlogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DateManipulator {

	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	public static Date getCurrentDate() {
		Date todaysDate = new Date();
		return todaysDate;
	}

	public static Date getCurrentDateAndTime() {
		Date todaysDateAndTime = new Date();
		return todaysDateAndTime;
	}

	public static boolean checkNewDay(Date oldDate, Date newDate) {

		Calendar oldCalendar = Calendar.getInstance();
		Calendar newCalendar = Calendar.getInstance();
		oldCalendar.setTime(oldDate);
		newCalendar.setTime(newDate);

		return oldCalendar.get(Calendar.YEAR) == newCalendar.get(Calendar.YEAR)
				&& oldCalendar.get(Calendar.DAY_OF_YEAR) == newCalendar.get(Calendar.DAY_OF_YEAR);
	}

	/*
	 * Compares a date from a row of the dataset and checks that it isn't in the future which would make it invalid
	 */
	
	public static boolean checkThatDateIsNotInTheFuture(String dateAndTimeToCheck) throws ParseException {

		Date dateToBeValidated = dateFormatter.parse(dateAndTimeToCheck);

		if (dateToBeValidated.after(getCurrentDateAndTime())) {
			return false;
		}

		return true;
	}
	protected static Date returnLastErrorLoggingDate() {
		Scanner dateScanner;
		try {
			File errorDate = new File("most_current_logging_date.txt");
			if(!errorDate.exists()){
				writeTodaysDateToErrorFile();
			}
			dateScanner = new Scanner(errorDate);
			Date errorDateToReturn = dateFormatter.parse(dateScanner.nextLine());
			dateScanner.close();
			return errorDateToReturn;
		} catch (FileNotFoundException | ParseException e) {
			System.out.println("File not found or parse exception in datemanipulator");
			e.printStackTrace();
		}
		return new Date();	
	}
	
	protected static void writeTodaysDateToErrorFile(){
		File errorRecord = new File("most_current_logging_date.txt");
		try {
			BufferedWriter dateRecorder = new BufferedWriter(new FileWriter(errorRecord, false));
			PrintWriter dateOutput = new PrintWriter(dateRecorder);
			dateOutput.write(dateFormatter.format(new Date()));
			dateOutput.close();
		} catch (IOException e) {
			System.out.println("Error writing to file...");
			e.printStackTrace();
		}
	}
}
