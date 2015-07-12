package com.thirtygames.zero.common.etc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CalendarUtil {

	
	 public static String toString(Calendar calendar, String pattern, TimeZone zone) {
	        SimpleDateFormat format = new SimpleDateFormat(pattern);
	        format.setTimeZone(zone);
	        return format.format(calendar.getTime());
	 }

	public static String toString(Calendar calendar, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(calendar.getTime());
	}

	public static Calendar toCalendar(String date, int yearLength, int monthLength, int dayLength) {
		int year = Integer.parseInt(date.substring(0, yearLength));
		int month = Integer.parseInt(date.substring(yearLength, yearLength + monthLength));
		int day = Integer.parseInt(date.substring(yearLength + monthLength, yearLength + monthLength + dayLength));
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal;
	}
}
