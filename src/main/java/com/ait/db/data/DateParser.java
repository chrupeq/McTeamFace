package com.ait.db.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateParser implements ParseDate {

	@Override
	public String parseCalendarToString(Calendar calendar, SimpleDateFormat simpleDateFormat) {
		String dateTimeAsString = simpleDateFormat.format(calendar.getTime());
		return dateTimeAsString;
	}
	@Override
	public Calendar parseStringToCalendar(String dateTime, SimpleDateFormat simpleDateFormat) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(simpleDateFormat.parse(dateTime));
		} catch(ParseException e) {
			System.out.println("Unable to parse date. " + e.toString());
		}
		return calendar;
	}
	public Calendar[] parseStringsToCalendarObjects(SimpleDateFormat simpleDateFormat, String...dateTime) {
		Calendar[] calendarArray = new Calendar[dateTime.length];
		for(int i=0; i < dateTime.length; i++) {
			calendarArray[i] = parseStringToCalendar(dateTime[i], simpleDateFormat);
		}
		return calendarArray;
	}
	public String convertFromEuropeanToAmericanDateFormat(String europeanDate) {
		String americanDate = europeanDate.substring(6,10) + "-" + europeanDate.substring(3, 5) + "-" + europeanDate.substring(0,2) + " " + europeanDate.substring(11,16);
		americanDate = americanDate + ":00";
		return americanDate;
	}		
}

