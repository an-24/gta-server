package biz.gelicon.gta.server.utils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import biz.gelicon.gta.server.GtaSystem;
import jersey.repackaged.com.google.common.collect.Lists;

public class DateUtils {
	
	public static Date getEndOfWeek(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(getStartOfWeek(date));
	    calendar.add(Calendar.DAY_OF_YEAR, 6);
	    return calendar.getTime();
	}

	public static Date getStartOfWeek(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    calendar.add(Calendar.DAY_OF_WEEK, 
				calendar.getFirstDayOfWeek() - calendar.get(Calendar.DAY_OF_WEEK));
	    return calendar.getTime();
	}
	
	public static Date getEndOfDay(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}

	public static Date getStartOfDay(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}

	public static Date getStartOfMonth(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    calendar.set(Calendar.DATE, 1);
	    return calendar.getTime();
	}

	public static Date getEndOfMonth(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    return calendar.getTime();
	}
	
	public static Date cleanTime(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
	    return calendar.getTime();
	}
	
	public static Date incDay(Date date, int dayCount) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    calendar.add(Calendar.DAY_OF_MONTH, dayCount);
	    return calendar.getTime();
	}

	public static Date incMonth(Date date, int mounthCount) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    calendar.add(Calendar.MONTH, mounthCount);
	    return calendar.getTime();
	}
	
	public static String getMonthName(int number, Locale locale) {
		DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
		return symbols.getMonths()[number];
	}
	
	public static List<String> getDayOfWeekNames(Locale locale) {
		DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
		Calendar cal = GregorianCalendar.getInstance(locale);
		int firstday = cal.getFirstDayOfWeek();
		ArrayList<String> list = Lists.newArrayList(Arrays.copyOfRange(symbols.getWeekdays(), firstday, 8));
		if(firstday>1)
			list.add(symbols.getWeekdays()[1]);
		return list;
	}

	public static int extractMonth(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	public static int extractYear(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int extractHour(Date date) {
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public static String codeMonth(int number, int year) {
		String m = number>9?String.valueOf(number):"0"+String.valueOf(number);
		return String.valueOf(year)+m;
	}
	
	public static int decodeMonth(String id) {
		return Integer.valueOf(id.substring(4));
	}
	
	public static int decodeYear(String id) {
		return Integer.valueOf(id.substring(0, 4));
	}

	public static Date newDate(int day, int month, int year) {
	    Calendar calendar = newCalendar();
	    calendar.set(Calendar.DAY_OF_MONTH, day);
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.YEAR, year);
		return cleanTime(calendar.getTime());
	}
	
	public static double substractDate(Date d1,Date d2) {
		long diff = Math.abs(d1.getTime() - d2.getTime());
		return 1.0*diff/(24 * 60 * 60 * 1000);		
	}

	public static Date convertToTimeZone(Date date, TimeZone tzDestination) {
	    TimeZone tzSource = TimeZone.getDefault();
	    if(tzSource==tzDestination) return date;
		// in default time zone
	    Calendar calendar = newCalendar();
	    calendar.setTime(date);
	    // to GMT
	    calendar.add(Calendar.MILLISECOND, -tzSource.getRawOffset());
        if (tzSource.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, -tzSource.getDSTSavings());
        }
	    // to tz
	    calendar.add(Calendar.MILLISECOND, tzDestination.getRawOffset());
        if (tzDestination.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, tzDestination.getDSTSavings());
        }
	    return calendar.getTime(); 
	}

	
	private static Calendar newCalendar() {
		Calendar calendar = Calendar.getInstance(GtaSystem.getLocale());
		return calendar;
	}

	
}
