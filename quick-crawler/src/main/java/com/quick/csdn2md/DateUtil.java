package com.quick.csdn2md;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class DateUtil {

  public DateUtil() {

  }

  /**
   * return current date value in format: yyyy-MM-dd
   *
   * @return String value
   */
  public static String getNowDate() {
    return dateToStringWithPattern(new Date(), "yyyy-MM-dd");
  }

  /**
   * return current time value in format: yyyy-MM-dd HH:mm:ss:sss
   *
   * @return String value
   */
  public static String getNowTime() {
    return dateToStringWithPattern(new Date(), "yyyy-MM-dd HH:mm:ss:sss");
  }

  /**
   * return time value of specified date in format: yyyy-MM-dd HH:mm
   *
   * @param date the specified date to convert
   * @return String value
   */
  public static String dateToString(Date date) {
    return dateToStringWithPattern(date, "yyyy-MM-dd HH:mm");
  }

  /**
   * return date value only of specified date in format: yyyy-MM-dd
   *
   * @param date the specified date to convert
   * @return String value
   */
  public static String dateToShortString(Date date) {
    return dateToStringWithPattern(date, "yyyy-MM-dd");
  }

  /**
   * return time value of specified date in format: yyyy-MM-dd HH:mm:ss
   *
   * @param date the specified date to convert
   * @return String value
   */
  public static String dateToLongString(Date date) {
    return dateToStringWithPattern(date, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * return time value only of specified date in format: HH:mm:ss
   *
   * @param date the specified date to convert
   * @return String value
   */
  public static String dateToTimeString(Date date) {
    return dateToStringWithPattern(date, "HH:mm:ss");
  }

  /**
   * return time value of specified date
   *
   * @param date the specified date to convert
   * @param pattern time format
   * @return String value
   */
  public static String dateToStringWithPattern(Date date, String pattern) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
      return simpleDateFormat.format(date);
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * split date value of specified date by '-'
   *
   * @param date the specified date to convert
   * @return String[] value
   */
  public static String[] SplitDate(Date date) {
    String s = dateToShortString(date);
    String[] temp = new String[3];
    StringTokenizer stringTokenizer = new StringTokenizer(s, "-");
    int i = 0;
    while (stringTokenizer.hasMoreTokens()) {
      temp[i] = stringTokenizer.nextToken();
      i++;
    }
    return temp;
  }

  /**
   * return string value of specified date in format: yyyy-MM-ddTHH:mm:ss
   *
   * @param date the specified date to convert
   * @return String value
   */
  public static String dateToBOMCStringDate(Date date) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String string = simpleDateFormat.format(date);
      string = StringToBOMCStringDate(string);
      return string;
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * return handled string value of date
   *
   * @param date string value to convert
   * @return String value
   */
  public static String StringToBOMCStringDate(String date) {
    return date.replace(" ", "T");
  }

  /**
   * return date value of specified string value in format: yyyy-MM-dd HH:mm:ss
   *
   * @param string string value to convert
   * @return Date value
   */
  public static Date stringToDate(String string) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return simpleDateFormat.parse(string);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * return date value of specified string value in format: HH:mm:ss
   *
   * @param string string value to convert
   * @return Date value
   */
  public static Date timeStringToDate(String string) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
      return simpleDateFormat.parse(string);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * return date value of specified string value in format: yyyy-MM-dd
   *
   * @param string string value to convert
   * @return Date value
   */
  public static Date stringToShortDate(String string) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      ParsePosition parsePosition = new ParsePosition(0);
      return simpleDateFormat.parse(string, parsePosition);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * return date value of specified string value in format: yyyyMMdd
   *
   * @param string string value to convert
   * @return Date value
   */
  public static Date stringToShortNoDate(String string) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      ParsePosition parsePosition = new ParsePosition(0);
      return simpleDateFormat.parse(string, parsePosition);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * return date value of now
   *
   * @return Date value
   */
  public static Date getNow() {
    return new Date();
  }

  /**
   * return unix timestamp of now
   *
   * @return long value
   */
  public static long getCurrentTimestamp() {
    return (new Date()).getTime();
  }

  /**
   * return unix timestamp of specified string value in format: yyyy-MM-dd
   *
   * @param string string value to convert
   * @return long value
   */
  public static long getTimestamp(String string) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      ParsePosition parsePosition = new ParsePosition(0);
      Date date = simpleDateFormat.parse(string, parsePosition);
      return date.getTime();
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * return unix timestamp of specified string value in format: yyyy-MM-dd HH:mm:ss
   *
   * @param string string value to convert
   * @return long value
   */
  public static long getStringToTimestamp(String string) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      ParsePosition parsePosition = new ParsePosition(0);
      Date date = simpleDateFormat.parse(string, parsePosition);
      return date.getTime();
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * return the time difference from a specified time to now in minutes
   *
   * @param timestamp unix timestamp of a specified time
   * @return long value
   */
  public static long getOffMinutes(long timestamp) {
    return getOffMinutes(timestamp, System.currentTimeMillis());
  }

  /**
   * return the time difference from two specified time
   *
   * @param left unix timestamp of the first specified time
   * @param right unix timestamp of the second specified time
   * @return long value
   */
  public static long getOffMinutes(long left, long right) {
    return (left - right) / 60000L;
  }

  /**
   * return string value of specified unix timestamp
   *
   * @param timestamp unix timestamp
   * @return String value
   */
  public static String LongToDateString(long timestamp) {
    DateFormat dateFormat;
    Date date;
    try {
      dateFormat = DateFormat.getDateTimeInstance();
      String dateString = String.valueOf(timestamp);
      date = new Date(Long.parseLong(dateString));
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return dateFormat.format(date);
  }

  /**
   * return date's weekday value of specified string value in format: yyyy-MM-dd Date first =
   * DateUtil.getMonday(today,Calendar.SUNDAY); Date last = DateUtil.getMonday(today,Calendar.SATURDAY);
   *
   * @param dateString String value of date
   * @param weekDay int index of weekday to get, first Calendar.SUNDAY, last Calendar.SATURDAY
   * @return Date value
   */
  public static Date getWeekDay(String dateString, int weekDay) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }
    // DAY_OF_WEEK
    // Field number for get and set indicating the day of the week. This field takes values
    // SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY
    calendar.set(Calendar.DAY_OF_WEEK, weekDay);
    calendar.add(Calendar.DATE, 1);
    return calendar.getTime();
  }

  /**
   * return the first day of the date's month of specified string value in format: yyyy-MM
   *
   * @param dateString String value of date
   * @return Date value
   */
  public static Date getMonthFirstDay(String dateString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
    Date date = null;
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }
    calendar.add(Calendar.DAY_OF_MONTH, 0);
    return calendar.getTime();
  }

  /**
   * return the last day of the date's month of specified string value in format: yyyy-MM
   *
   * @param dateString String value of date
   * @return Date value
   */
  public static Date getMonthLastDay(String dateString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
    Date date = null;
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }
    calendar.add(Calendar.MONTH, 1);
    calendar.add(Calendar.DATE, -1);
    return calendar.getTime();
  }

  /**
   * return the first day of the date's year of specified string value in format: yyyy
   *
   * @param dateString String value of date
   * @return Date value
   */
  public static Date getYearFirstDay(String dateString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
    Date date = null;
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }
    calendar.add(Calendar.DAY_OF_YEAR, 0);
    return calendar.getTime();
  }

  /**
   * return the last day of the date's year of specified string value in format: yyyy
   *
   * @param dateString String value
   * @return Date value
   */
  public static Date getYearLastDay(String dateString) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
    Date date = null;
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();
    if (date != null) {
      calendar.setTime(date);
    }
    calendar.add(Calendar.YEAR, 1);
    calendar.add(Calendar.DATE, -1);
    return calendar.getTime();
  }

  /**
   * return date value with specified field value
   *
   * @param date Date value
   * @param field int Date filed, such as Calendar.DAY_OF_MONTH
   * @param amount int the value of the field to set
   * @return Date value
   */
  public static Date getDate(Date date, int field, int amount) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(field, amount);
    return calendar.getTime();
  }
}
