/**SmartSoftware User - Service */
/**
 * Description: date time utility
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */
package com.ss.util;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class UtilDateAndTime {

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	/**
	 * Method will return date in UTC format
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Date localToUTC() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date gmt = new Date(sdf.format(date));
		return gmt;
	}
	
	/**
	 * Method will convert UTC date to local date
	 * @param date
	 * @return
	 */
	public Date utcToLocalDate(Date date) {
		String timeZone = Calendar.getInstance().getTimeZone().getID();
		Date local = new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
		return local;
	}

	/**
	 * @param dateInString
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String dateInString) throws ParseException {
		Date date = new Date();

		date = formatter.parse(dateInString);

		return date;
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date ddmmyyyyStringToDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date date1 = formatter.parse(date);
			return date1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @param date
	 * @return
	 */
	public static String dateToStringddmmyyyy(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			String formatDate = formatter.format(date);
			return formatDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date ddmmyyyyStringTimeToDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
		try {
			Date date1 = formatter.parse(date);
			return date1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param date
	 * @return
	 */
	public static String dateToStringhhmmaa(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
		try {
			String formatDate = formatter.format(date);
			return formatDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param time
	 * @return
	 */
	public static Time getTimeFromStringFrom12Formats(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
		try {
			Date date1 = formatter.parse(time);
			return new java.sql.Time(date1.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Time getTimeFromStringFrom24Formats(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		try {
			Date date1 = formatter.parse(time);
			return new java.sql.Time(date1.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String convertTimeToString12Formats(Date time) {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat outPutFormat = new SimpleDateFormat("hh:mm aa");
		try {
			Date date1 = formatter.parse(""+time);
			return outPutFormat.format(date1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String convertTimeToString24Formats(Date time) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat outPutFormat = new SimpleDateFormat("HH:mm");
		try {
			Date date1 = formatter.parse(""+time);
			return outPutFormat.format(date1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String convertDateToStringTime24Formats(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		try {
			String HHMMSS = formatter.format(date);
			return HHMMSS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
/*	public static String convertDateTo24HoursFormat(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		
		try {
			Date date1 = formatter.format(date)
			return outPutFormat.format(date1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	/**
	 * @param date
	 * @return
	 */
	public static String convertDateDDMMYYYYFormatToDBFormat(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = formatter.parse(date);
			return dbFormat.format(date1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Integer getWeekDayNumber(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}
	
	public static boolean chechCurrentTimeLiesBetweenTwoTimes(String startTime , String endTime, String currentTime)
	{
		try {
		    Date time1 = new SimpleDateFormat("HH:mm:ss").parse(startTime);
		    Calendar calendar1 = Calendar.getInstance();
		    calendar1.setTime(time1);

		    Date time2 = new SimpleDateFormat("HH:mm:ss").parse(endTime);
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.setTime(time2);
		    calendar2.add(Calendar.DATE, 1);

		    
		    Date d = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
		    Calendar calendar3 = Calendar.getInstance();
		    calendar3.setTime(d);
		    calendar3.add(Calendar.DATE, 1);

		    Date x = calendar3.getTime();
		    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
		        //checkes whether the current time is between 14:49:00 and 20:11:13.
		        return true;
		    }
		    
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return false;
	}

}
