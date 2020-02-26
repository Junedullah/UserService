/**SmartSoftware User - Service */
/**
 * Description: Util Time Intervals
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */
package com.ss.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UtilTimeIntervals {

    // format 24hre ex. 12:12 , 17:15
    private static String  HOUR_FORMAT = "HH:mm:ss";

    private UtilTimeIntervals() {    }

    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
        return  sdfHour.format(cal.getTime());
    }

    /**
     * @param  target  hour to check
     * @param  start   interval start
     * @param  end     interval end
     * @return true    true if the given hour is between
     */
    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }

    /**
     * @param  start   interval start
     * @param  end     interval end
     * @return true    true if the current hour is between
     */
    public static boolean isNowInInterval(String start, String end) {
        return UtilTimeIntervals.isHourInInterval
            (UtilTimeIntervals.getCurrentHour(), start, end);
    }

    //    TEST
    public static void main (String[] args) {
      String now = UtilTimeIntervals.getCurrentHour();
      String start = "00:01:00";
      String end   = "14:27:50";
      System.err.println(now + " between " + start + "-" + end + "?");
      System.err.println(UtilTimeIntervals.isHourInInterval(now,start,end));
      System.err.println(UtilTimeIntervals.isNowInInterval(start,end));
      /*
       * output example :
       *   21:01 between 14:00-14:26?
       *   false
       *
       */
      
    }
}