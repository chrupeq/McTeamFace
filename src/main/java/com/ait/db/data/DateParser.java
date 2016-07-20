package com.ait.db.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateParser implements ParseDate {

	@Override
	public String parseCalendarToString(final Calendar calendar, final SimpleDateFormat simpleDateFormat) {
		final String dateTimeAsString = simpleDateFormat.format(calendar.getTime());
		return dateTimeAsString;
	}
	@Override
	public Calendar parseStringToCalendar(final String dateTime, final SimpleDateFormat simpleDateFormat) {
		final Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(dateTime));
		} catch(ParseException e) {
			System.out.println("Unable to parse date. " + e.toString());
		}
		return calendar;
	}
	public Calendar[] parseStringsToCalendarObjects(final SimpleDateFormat simpleDateFormat, final String...dateTime) {
		Calendar[] calendarArray = new Calendar[dateTime.length];
		for(int i=0; i < dateTime.length; i++) {
			calendarArray[i] = parseStringToCalendar(dateTime[i], simpleDateFormat);
		}
		return calendarArray;
	}
	public String convertFromEuropeanToAmericanDateFormat(final String europeanDate) {
		String americanDate = europeanDate.substring(6,10) + "-" + europeanDate.substring(3, 5) + "-" + europeanDate.substring(0,2) + " " + europeanDate.substring(11,16);
		americanDate = americanDate + ":00";
		System.out.println(americanDate);
		return americanDate;
	}		
}

