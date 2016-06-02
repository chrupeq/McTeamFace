package com.errorlogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManipulator {

	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	protected static Date getCurrentDate() {
		Date todaysDate = new Date();
		return todaysDate;
	}

	public static Date getCurrentDateAndTime() {
		Date todaysDateAndTime = new Date();
		return todaysDateAndTime;
	}

	protected static boolean checkNewDay(Date oldDate, Date newDate) {

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

}
