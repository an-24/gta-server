package biz.gelicon.gta.server.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date getEndOfWeek(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(getStartOfWeek(date));
	    calendar.add(Calendar.DAY_OF_YEAR, 6);
	    return calendar.getTime();
	}

	public static Date getStartOfWeek(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.DAY_OF_WEEK, 
				calendar.getFirstDayOfWeek() - calendar.get(Calendar.DAY_OF_WEEK));
	    return calendar.getTime();
	}
	
	public static Date getEndOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}

	public static Date getStartOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}

	public static Date getStartOfMonth(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.DATE, 1);
	    return calendar.getTime();
	}

	public static Date getEndOfMonth(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	    return calendar.getTime();
	}
}
