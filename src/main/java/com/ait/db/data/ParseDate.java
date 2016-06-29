package com.ait.db.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public interface ParseDate {
	String parseCalendarToString(Calendar calendar, SimpleDateFormat simpleDateFormat);
	Calendar parseStringToCalendar(String dateTime, SimpleDateFormat simpleDateFormat);
}
