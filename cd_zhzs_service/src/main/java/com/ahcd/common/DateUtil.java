package com.ahcd.common;

/**
 * <p>Title: 时间和日期的工具类</p>
 * <p>Description: DateUtil类包含了标准的时间和日期格式，以及这些格式在字符串及日期之间转换的方法</p>
 * <p>Copyright: Copyright (c) 2007 advance,Inc. All Rights Reserved</p>
 * <p>Company: advance,Inc.</p>
 * @author advance
 * @version 1.0
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	// ~ Static fields/initializers
	// =============================================

	private static String datePattern = "MM/dd/yyyy";

	private static String timePattern = datePattern + " HH:MM a";
	private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");;
	private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 *
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final String date2Str(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final String date2Str(String pattern, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			return null;
		}

		return (date);
	}

	public static final Date str2Date(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			return null;
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 *
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 *
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 *
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			System.out.print("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 *
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 *
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 *
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {

			aDate = convertStringToDate(datePattern, strDate);
		} catch (ParseException pe) {
			// log.error("Could not convert '" + strDate
			// + "' to a date, throwing exception");
			pe.printStackTrace();
			return null;

		}
		return aDate;
	}

	// 日期格式转换成时间戳
	public static long getTimeStamp(String pattern, String strDate) {
		long returnTimeStamp = 0;
		Date aDate = null;
		try {
			aDate = convertStringToDate(pattern, strDate);
		} catch (ParseException pe) {
			aDate = null;
		}
		if (aDate == null) {
			returnTimeStamp = 0;
		} else {
			returnTimeStamp = aDate.getTime();
		}
		return returnTimeStamp;
	}

	// 获取当前日期的邮戳
	public static long getNowTimeStamp() {
		long returnTimeStamp = 0;
		Date aDate = null;
		try {
			aDate = convertStringToDate("yyyy-MM-dd HH:mm:ss", getNowDateTime());
		} catch (ParseException pe) {
			aDate = null;
		}
		if (aDate == null) {
			returnTimeStamp = 0;
		} else {
			returnTimeStamp = aDate.getTime();
		}
		return returnTimeStamp;
	}

	/**
	 * 得到格式化后的系统当前日期
	 * 
	 * @param strScheme
	 *            格式模式字符串
	 * @return 格式化后的系统当前时间，如果有异常产生，返回空串""
	 * @see java.util.SimpleDateFormat
	 */
	public static final String getNowDateTime(String strScheme) {
		String strReturn = null;
		Date now = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
			strReturn = sdf.format(now);
		} catch (Exception e) {
			strReturn = "";
		}
		return strReturn;
	}

	public static final String getNowDateTime() {
		String strReturn = null;
		Date now = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strReturn = sdf.format(now);
		} catch (Exception e) {
			strReturn = "";
		}
		return strReturn;
	}

	/**
	 * 转化日期格式"MM/dd/YY、MM.dd.YY、MM-dd-YY、MM/dd/YY"，并输出为正常的格式yyyy-MM-dd
	 * 
	 * @param strDate
	 *            待转换的日期格式
	 * @return 格式化后的日期，如果有异常产生，返回空串""
	 * @see java.util.SimpleDateFormat
	 */
	public static final String convertNormalDate(String strDate) {
		String strReturn = null;
		// 先把传入参数分格符转换成java认识的分格符
		String[] date_arr = strDate.split("\\.|\\/|\\-");
		try {
			if (date_arr.length == 3) {
				if (date_arr[2].length() != 4) {
					String nowYear = getNowDateTime("yyyy");
					date_arr[2] = nowYear.substring(0, 2) + date_arr[2];
				}
				strReturn = DateUtil.getDateTime("yyyy-MM-dd", convertStringToDate(combineStringArray(date_arr, "/")));
			}

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * 将字符串数组使用指定的分隔符合并成一个字符串。
	 * 
	 * @param array
	 *            字符串数组
	 * @param delim
	 *            分隔符，为null的时候使用""作为分隔符（即没有分隔符）
	 * @return 合并后的字符串
	 * @since 0.4
	 */
	public static final String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	public static final int getWeekNum(String strWeek) {
		int returnValue = 0;
		if (strWeek.equals("Mon")) {
			returnValue = 1;
		} else if (strWeek.equals("Tue")) {
			returnValue = 2;
		} else if (strWeek.equals("Wed")) {
			returnValue = 3;
		} else if (strWeek.equals("Thu")) {
			returnValue = 4;
		} else if (strWeek.equals("Fri")) {
			returnValue = 5;
		} else if (strWeek.equals("Sat")) {
			returnValue = 6;
		} else if (strWeek.equals("Sun")) {
			returnValue = 0;
		} else if (strWeek == null) {
			returnValue = 0;
		}

		return returnValue;
	}

	/**
	 * 获取日期字符串中的中文时间表示字符串
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String getSabreTime(String strDate) {
		String strReturn = "";
		try {

			Date d = DateUtil.str2Date("yyyy-MM-dd HH:mm:ss", CTool.replace(strDate, "T", " "));
			strReturn = DateUtil.date2Str("hh:mm aaa", d);

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * 获取日期字符串中的中文日期表示字符串
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String getSabreDate(String strDate) {
		String strReturn = "";
		try {
			String p = null;
			if (strDate.length() > 10)
				p = "yyyy-MM-dd HH:mm:ss";
			else
				p = "yyyy-MM-dd";
			Date d = DateUtil.str2Date(p, CTool.replace(strDate, "T", " "));
			strReturn = DateUtil.date2Str("EEE d-MMM", d);

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * 获取日期字符串的中文日期时间表示
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String getSabreDateTime(String strDate) {
		String strReturn = "";
		try {
			String p = null;
			if (strDate.length() > 10)
				p = "yyyy-MM-dd HH:mm:ss";
			else
				p = "yyyy-MM-dd";
			Date d = DateUtil.str2Date(p, CTool.replace(strDate, "T", " "));
			strReturn = DateUtil.date2Str("EEE d-MMM hh:mm aaa", d);

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * 得到格式化后的指定日期
	 * 
	 * @param strScheme
	 *            格式模式字符串
	 * @param date
	 *            指定的日期值
	 * @return 格式化后的指定日期，如果有异常产生，返回空串""
	 * @see java.util.SimpleDateFormat
	 */
	public static final String getDateTime(Date date, String strScheme) {
		String strReturn = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
			strReturn = sdf.format(date);
		} catch (Exception e) {
			strReturn = "";
		}

		return strReturn;
	}

	/**
	 * 获取日期
	 * 
	 * @param timeType
	 *            时间类型，譬如：Calendar.DAY_OF_YEAR
	 * @param timenum
	 *            时间数字，譬如：-1 昨天，0 今天，1 明天
	 * @return 日期
	 */
	public static final Date getDateFromNow(int timeType, int timenum) {
		Calendar cld = Calendar.getInstance();
		cld.set(timeType, cld.get(timeType) + timenum);
		return cld.getTime();
	}

	/**
	 * 获取日期
	 * 
	 * @param timeType
	 *            时间类型，譬如：Calendar.DAY_OF_YEAR
	 * @param timenum
	 *            时间数字，譬如：-1 昨天，0 今天，1 明天
	 * @param format_string
	 *            时间格式，譬如："yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static final String getDateFromNow(int timeType, int timenum, String format_string) {
		if ((format_string == null) || (format_string.equals("")))
			format_string = "yyyy-MM-dd HH:mm:ss";
		Calendar cld = Calendar.getInstance();
		Date date = null;
		DateFormat df = new SimpleDateFormat(format_string);
		cld.set(timeType, cld.get(timeType) + timenum);
		date = cld.getTime();
		return df.format(date);
	}

	/**
	 * 获取当前日期的字符串
	 * 
	 * @param format_string
	 *            时间格式，譬如："yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static final String getDateNow(String format_string) {
		if ((format_string == null) || (format_string.equals("")))
			format_string = "yyyy-MM-dd HH:mm:ss";
		Calendar cld = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(format_string);
		return df.format(cld.getTime());
	}

	public static String formateDate(Date date, String format_string) {
		if ((format_string == null) || (format_string.equals("")))
			format_string = "yyyy-MM-dd HH:mm:ss";
		DateFormat df = new SimpleDateFormat(format_string);
		return df.format(date);
	}

	/**
	 * 
	 * 功能说明 :当前季度的开始时间
	 * 
	 * @author : fei yang
	 * @version ：2017年2月24日 下午3:52:19
	 * @return
	 */
	public static Date getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 
	 * 功能说明 : 当前季度的结束时间
	 * 
	 * @author : fei yang
	 * @version ：2017年2月24日 下午3:52:12
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 
	 * 功能说明 :获取前/后半年的开始时间
	 * 
	 * @author : fei yang
	 * @version ：2017年2月24日 下午3:52:05
	 * @return
	 */
	public static Date getHalfYearStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 0);
			} else if (currentMonth >= 7 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 6);
			}
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;

	}

	/**
	 * 
	 * 功能说明 :获取前/后半年的结束时间
	 * 
	 * @author : fei yang
	 * @version ：2017年2月24日 下午3:51:48
	 * @return
	 */
	public static Date getHalfYearEndTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 
	   * 功能说明    :  时间字符串转换 20130101==>2013年1月1日
	   * @author   : fei yang 
	   * @version ：2017年5月16日 下午3:59:57 
	   * @param dateStr 时间字符串
	   * @param oldFormatStr 转换前格式
	   * @param newFormatStr	 转换后格式
	   * @return
	 */
	public static String changeFormat(String dateStr, String oldFormatStr, String newFormatStr) {
		if (StringUtil.isBlank(dateStr) || StringUtil.isBlank(oldFormatStr) || StringUtil.isBlank(newFormatStr)) {
			return dateStr;
		}
		try {
			SimpleDateFormat oldFormatter = new SimpleDateFormat(oldFormatStr);
			oldFormatter.setLenient(false);
			SimpleDateFormat newFormatter = new SimpleDateFormat(newFormatStr);
			newFormatter.setLenient(false);
			Date currentTime = oldFormatter.parse(dateStr);
			String dateString = newFormatter.format(currentTime);
			return dateString;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return dateStr;
	}

}