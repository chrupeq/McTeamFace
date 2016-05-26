package com.errorlogger;

import java.util.Calendar;
import java.util.Date;

public class DateManipulator {

	protected static Date getCurrentDate() {
		Date todaysDate = new Date();
		return todaysDate;
	}

	protected static Date getCurrentDateAndTime() {
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

}
