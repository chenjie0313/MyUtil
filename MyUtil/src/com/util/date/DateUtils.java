package com.util.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/** yyyy-MM-dd HH:mm:ss **/
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd **/
	public static final String DATE_FORMAT2 = "yyyy-MM-dd";
	/** yyyy */
	public static final String DATE_FORMAT3 = "yyyy";
	/** yyyyMMdd */
	public static final String DATE_FORMAT4 = "yyyyMMdd";
	/** yyyyMM */
	public static final String DATE_FORMAT5 = "yyyyMM";
	/** MMdd */
	public static final String DATE_FORMAT6 = "MMdd";

	public static Timestamp getCurrent() {
		return new Timestamp(getTime().getTimeInMillis());
	}

	public static String getDateString(String pattern) {
		DateFormat dfmt = new SimpleDateFormat(pattern);
		return dfmt.format(new Date());
	}

	public static String getTime(String dateFormat) {
		Calendar now = Calendar.getInstance();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
	}

	public static String getTime2(String dateFormat) {
		Calendar now = Calendar.getInstance();
		return new SimpleDateFormat("yyyyMMddHHmmss").format(now.getTime());
	}

	public static String parse(long timeMillis, String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		return sf.format(new Date(timeMillis));
	}

	public static Calendar getTime() {
		Calendar time = Calendar.getInstance();
		return time;
	}

	public static Calendar getTime(long timeMillis) {
		Calendar time = getTime();
		time.setTimeInMillis(timeMillis);
		return time;
	}

	public static Date getDate() {
		return getTime().getTime();
	}

	public static Date getDate(long timeMillis) {
		return getTime(timeMillis).getTime();
	}

	public static String getYYYY() {
		return new SimpleDateFormat("yyyy").format(getDate());
	}

	public static String getYYYY(long timeMillis) {
		return new SimpleDateFormat("yyyy").format(getDate(timeMillis));
	}

	public static String getYYYYMMDD() {
		return new SimpleDateFormat("yyyyMMdd").format(getDate());
	}

	public static String getYYYYMMDD(long time) {
		return new SimpleDateFormat("yyyyMMdd").format(getDate(time));
	}

	public static String getHHmmss() {
		return new SimpleDateFormat("HHmmss").format(getDate());
	}

	public static String getYYYYMM() {
		return new SimpleDateFormat("yyyyMM").format(getDate());
	}

	public static String getYYYYMM(long timeMillis) {
		return new SimpleDateFormat("yyyyMM").format(getDate(timeMillis));
	}

	public static String getMMDD() {
		return new SimpleDateFormat("MMdd").format(getTime());
	}

	public static String getMMDD(long timeMillis) {
		return new SimpleDateFormat("MMdd").format(getDate(timeMillis));
	}

	public static String getDD(long timeMillis) {
		return new SimpleDateFormat("dd").format(getDate(timeMillis));
	}

	public static void main(String[] arg) {
		System.out.println(DateUtils.getYYYY());
	}

	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	public static Timestamp getTimestamp(String time) {
		Timestamp ts = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			ts = new Timestamp(format.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}

	public static Timestamp getTimestampNext(String time) {
		Timestamp ts = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(time));
			c.add(Calendar.DATE, 1);
			ts = new Timestamp(c.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * 获取当前时间
	 * 
	 * @param dateformat
	 * @return
	 */
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}
}
