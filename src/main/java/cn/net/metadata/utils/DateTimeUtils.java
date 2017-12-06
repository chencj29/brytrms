package cn.net.metadata.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author chenziang
 *         createddate 2006-6-19 22:38:54
 *         email : dankes@163.com
 *         Blog : http://blog.csdn.net/dankes/
 */
public class DateTimeUtils {

    public static final String DATE_SEPARATOR = "-";
    public static final String TIME_SEPARATOR = ":";
    public static final String STRING_SPACE = " ";
    public static final String DATE_TIME_SEPARATOR = STRING_SPACE;
    /**
     * String to prepend to time [HH:MM:SS.d]
     * to create a Timestamp escape string ["0004-06-30"].
     */
    public static final String TIMESTAMP_DATE_ZERO = "0004-06-30";
    /**
     * String to append to date [YEAR-MM-DD]
     * to create a Timestamp escape string [" 00:00:00.0"].
     * Note: includes leading space.
     */
    public static final String TIMESTAMP_TIME_ZERO = " 00:00:00.0";
    /**
     * Default style for dates and times.
     */
    public static int DEFAULT = DateFormat.DEFAULT;
    /**
     * Short style for dates and times.
     */
    public static int SHORT = DateFormat.SHORT;
    /**
     * Medium style for dates and times.
     */
    public static int MEDIUM = DateFormat.MEDIUM;
    /**
     * Long style for dates and times.
     */
    public static int LONG = DateFormat.LONG;
    /**
     * Full style for dates and times.
     */
    public static int FULL = DateFormat.FULL;
    /**
     * A "default" date format.
     */
    public static String ESCAPE_DATE_PATTERN = "yyyy-mm-dd";

//	/**
//	 * Formats a Date as a fifteen character long String made up of the Date's
//	 * padded millisecond value.
//	 *
//	 * @return a Date encoded as a String.
//	 */
//	public static final String dateToMillis(Date date) {
//		return StringUtils.zeroPadString(Long.toString(date.getTime()), 15);
//	}
    /**
     * Escape string representing
     */
    public static String ZERO_TIMESTAMP_DISPLAY = "4-06-30 00:00:00";
    /**
     * Timestamp representing ""November 30, 0002 00:00:00".
     */
    public static Timestamp ZERO_TIMESTAMP = new Timestamp((long) 00000000000000);
    public static String NULL_TIMESTAMP_DISPLAY = "1970-01-01 00:00:00";
    /**
     * Value needed to create Timestamp representing
     * "January 1, 1970 00:00:00".
     * From the documentation, you would think this would be
     * Timestamp(0), but empirical tests show it to be
     * Timestamp(18000000).
     */
    public static long NULL_TIME = (long) 18000000;
    /**
     * Timestamp representing "January 1, 1970 00:00:00".
     */
    public static Timestamp NULL_TIMESTAMP = new Timestamp(NULL_TIME);
    /**
     * Timestamp representing "December 31, 2029, 23:59:59.9"
     */
    public static Timestamp MAX_TIMESTAMP = Timestamp.valueOf(
            "2029-12-31 23:59:59.999");
    /**
     * Default lenient setting for getDate.
     */
    private static boolean LENIENT_DATE = false;

    public static Timestamp getNowTimestamp() {
        Date date = new Date();
        return new java.sql.Timestamp(date.getTime());
    }

    public static java.sql.Date getNowSqlDate() {
        Date date = new Date();
        return new java.sql.Date(date.getTime());
    }

    public static String getCurrentYear() {
        try {
            Date date = new Date();
            SimpleDateFormat shortdf = new SimpleDateFormat("yyyy");
            return shortdf.format(date);
        } catch (IllegalArgumentException iae) {
            return "";
        }
    }

    public static String getCurrentMonth() {
        try {
            Date date = new Date();
            SimpleDateFormat shortdf = new SimpleDateFormat("MM");
            return shortdf.format(date);
        } catch (IllegalArgumentException iae) {
            return "";
        }
    }

    public static final String getCurrentDate() {
        try {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(date);
        } catch (IllegalArgumentException iae) {
            return "";
        }
    }

    public static final String getFormatCurrentDate(String format) {
        try {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (IllegalArgumentException iae) {
            return "";
        }
    }

    public static final String toChineseString(Date date) {
        try {
            SimpleDateFormat shortdf = new SimpleDateFormat("yyyyÄêMMÔÂddÈÕ");
            return shortdf.format(date);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return "";
        }
    }

    public static final String dateToShortString(Date date) {
        return dateToShortString(date, "yyyy-MM-dd");
    }

    public static final String dateToShortString(Date date, String format) {
        try {
            SimpleDateFormat shortdf = new SimpleDateFormat(format);
            return shortdf.format(date);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return "";
        }
    }

    public static final String dateToLangString(Date date) {
        try {
            SimpleDateFormat longdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return longdf.format(date);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return "";
        }
    }

    public static final java.sql.Timestamp StrToTimestamp(String date) {
        try {
            SimpleDateFormat longdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return new java.sql.Timestamp(longdf.parse(date).getTime());
        } catch (Exception iae) {
            iae.printStackTrace();
            return java.sql.Timestamp.valueOf("1970-01-01 00:00:01.000000000");
        }
    }

    public static final java.sql.Date StrToSqlDate(String date) {
        try {
            return java.sql.Date.valueOf(date);
        } catch (Exception iae) {
            iae.printStackTrace();
            return java.sql.Date.valueOf("1970-01-01");
        }
    }

    public static final long dateMillis(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long val = 0;
        try {
            val = format.parse(date).getTime();
        } catch (Exception ex) {

        }
        return val;
    }

    public static final Date long2Date(String longVal) {
        Date date = new Date(Long.parseLong(longVal));
        return date;
    }

    /**
     * Convert String to Date using given format.
     * Returns null if conversion fails.
     * Set lenient=false to disallow dates like 2001-9-32.
     * http://java.sun.com/j2se/1.4/docs/api/java/text/SimpleDateFormat.html
     *
     * @author Hal Deadman
     */
    public static Date getDate(String dateDisplay,
                               String format, boolean lenient) {
        if (dateDisplay == null) {
            return null;
        }
        DateFormat df = null;
        try {
            if (format == null) {
                df = new SimpleDateFormat();
            } else {
                df = new SimpleDateFormat(format);
            }
            // setLenient avoids allowing dates like 9/32/2001
            // which would otherwise parse to 10/2/2001
            df.setLenient(false);
            return df.parse(dateDisplay);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert String to Date using given format.
     * Returns null if conversion fails.
     * Uses "strict" coNversion (lenient=false).
     *
     * @author Hal Deadman
     */
    public static Date getDate(String dateDisplay, String format) {
        return getDate(dateDisplay, format, LENIENT_DATE);
    }

    /**
     * Convert String to Date using a medium (weekday day month year) format.
     * Returns null if conversion fails.
     * Uses "strict" coNversion (lenient=false).
     *
     * @author Hal Deadman
     */
    public static Date getDate(String dateDisplay) {
        return getDate(dateDisplay, null, LENIENT_DATE);
    }

    /**
     * Return Date value using a String.
     * Null or conversion error returns null.
     *
     * @param String representing Date
     */
    public static Date toDate(String string) {
        if (string == null) {
            return null;
        } else {
            try {
                return getDate(string);
            } catch (Throwable t) {
                return null;
            }
        }
    }

    /**
     * Convert date to String for given locale in given style.
     * A null locale will return the default locale.
     */
    public static String getDate(Date date, Locale locale, int style) {
        if (locale == null) {
            return (DateFormat.getDateInstance(style).format(date));
        }
        return (DateFormat.getDateInstance(style, locale).format(date));
    }

    /**
     * Convert date to String for default locale in given style.
     * A null locale will return the default locale.
     */
    public static String getDate(Date date, int style) {
        return getDate(date, (Locale) null, style);
    }

    /**
     * Convert date to String for default locale in DEFAULT style.
     * A null locale will return the default locale.
     */
    public static String getDate(Date date) {
        return getDate(date, (Locale) null, DEFAULT);
    }

    /**
     * Return String value representing Date.
     * Null returns null.
     *
     * @param Date
     */
    public static String toString(Date date) {
        if (date == null) {
            return null;
        } else {
            return getDate(date);
        }
    }

    /**
     * Return String value representing Date.
     * Null returns null.
     *
     * @param Date
     */
    public static String toEscape(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = null;
        try {
            df = new SimpleDateFormat(ESCAPE_DATE_PATTERN);
        } catch (Throwable t) {
            return null;
        }
        df.setLenient(false);
        return df.format(date);
    }

    /**
     * Return the String representing "January 1, 1970 00:00:00".
     */
    public static String getTimestampDisplayNull() {
        return NULL_TIMESTAMP_DISPLAY;
    }

    /**
     * Return the String representing the current timestamp;
     */
    public static String getTimestampDisplay() {
        return getTimestamp(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * @deprecated Use getTimestampDisplay.
     */
    public static String getTimestampText() {
        return getTimestampDisplay();
    }

    /**
     * Return null if timestamp is null or equals
     * "January 1, 1970 00:00:00".
     */
    public static boolean isNull(Timestamp timestamp) {
        return ((timestamp == null) || (timestamp.getTime() == NULL_TIME));
    }

    /**
     * Factory method to return timestamp initialized to
     * current system time.
     * For timestamp as a String in the default format,
     * use <code>getTimestamp().toString()</code>.
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Convert timestamp to String for given locale in given style.
     * A null locale will return the default locale.
     */
    public static String getTimestamp(Timestamp timestamp,
                                      Locale locale, int style) {
        Date date = (Date) timestamp;
        if (locale == null) {
            return (DateFormat.getDateTimeInstance(style, style).
                    format(date));
        }
        return (DateFormat.getDateTimeInstance(style, style, locale).
                format(date));
    }

    /**
     * Convert date to String for default locale in given style.
     * A null locale will return the default locale.
     */
    public static String getTimestamp(Timestamp timestamp,
                                      int style) {
        return getTimestamp(timestamp, (Locale) null, style);
    }

    /**
     * Convert date to String for default locale in DEFAULT style.
     * A null locale will return the default locale.
     */
    public static String getTimestamp(Timestamp timestamp) {
        return getTimestamp(timestamp, (Locale) null, DEFAULT);
    }

    /**
     * Return Timestamp value using a String.
     * Null or conversion error returns null.
     *
     * @param String representing Timestamp
     */
    public static Timestamp toTimestamp(String string) {
        if (string == null) {
            return null;
        } else {
            try {
                return Timestamp.valueOf(string);
            } catch (Throwable t) {
                return null;
            }
        }
    }

    /**
     * Return a Timestamp based on the parameters.
     * Any nulls or conversion error returns null.
     *
     * @param year  The year
     * @param month The month
     * @param day   The day
     * @returns Timestamp for year-month-day
     */
    public static Timestamp toTimestamp(String year, String month, String day) {

        if ((null == year) || (null == month) || (null == day)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        // YEAR-MM-DD 00:00:00.0
        sb.append(year);
        sb.append(DATE_SEPARATOR);
        sb.append(month);
        sb.append(DATE_SEPARATOR);
        sb.append(day);

        sb.append(TIMESTAMP_TIME_ZERO);

        return toTimestamp(sb.toString());
    }

    /**
     * Return String value representing Timestamp.
     * Null returns null.
     *
     * @param Timestamp
     */
    public static String toString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toString();
        }
    }

    // µ±Ç°ÈÕÆÚ¼Ó¼õnÌìºóµÄÈÕÆÚ£¬·µ»ØString (yyyy-mm-dd)
    public static String nDaysAftertoday(int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        //rightNow.add(Calendar.DAY_OF_MONTH,-1);
        rightNow.add(Calendar.DAY_OF_MONTH, +n);
        return df.format(rightNow.getTime());
    }

    // µ±Ç°ÈÕÆÚ¼Ó¼õnÌìºóµÄÈÕÆÚ£¬·µ»ØDate
    public static Date nDaysAfterNowDate(int n) {
        Calendar rightNow = Calendar.getInstance();
        //rightNow.add(Calendar.DAY_OF_MONTH,-1);
        rightNow.add(Calendar.DAY_OF_MONTH, +n);
        return rightNow.getTime();
    }

    // ¸ø¶¨Ò»¸öÈÕÆÚÐÍ×Ö·û´®£¬·µ»Ø¼Ó¼õnÌìºóµÄÈÕÆÚÐÍ×Ö·û´®
    public static String nDaysAfterOneDateString(String basicDate, int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = null;
        try {
            tmpDate = df.parse(basicDate);
        } catch (Exception e) {
            // ÈÕÆÚÐÍ×Ö·û´®¸ñÊ½´íÎó
        }
        long nDay = (tmpDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n) *
                (24 * 60 * 60 * 1000);
        tmpDate.setTime(nDay);

        return df.format(tmpDate);
    }

    // ¸ø¶¨Ò»¸öÈÕÆÚ£¬·µ»Ø¼Ó¼õnÌìºóµÄÈÕÆÚ£¬·µ»ØDate
    public static Date nDaysAfterOneDate(Date basicDate, int n) {
        long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n) *
                (24 * 60 * 60 * 1000);
        basicDate.setTime(nDay);

        return basicDate;
    }

    // µ±Ç°ÈÕÆÚ¼Ó¼õn¸öÔÂºóµÄÈÕÆÚ£¬·µ»ØString (yyyy-mm-dd)
    public static String nMonthsAftertoday(int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        //rightNow.add(Calendar.DAY_OF_MONTH,-1);
        rightNow.add(+n, Calendar.MONTH);
        return df.format(rightNow.getTime());
    }

    // µ±Ç°ÈÕÆÚ¼Ó¼õn¸öÔÂºóµÄÈÕÆÚ£¬·µ»ØDate
    public static Date nMonthsAfterNowDate(int n) {
        Calendar rightNow = Calendar.getInstance();
        //rightNow.add(Calendar.DAY_OF_MONTH,-1);
        rightNow.add(Calendar.MONTH, +n);
        return rightNow.getTime();
    }

    // µ±Ç°ÈÕÆÚ¼Ó¼õn¸öÔÂºóµÄÈÕÆÚ£¬·µ»ØDate
    public static Date nMonthsAfterOneDate(Date basicDate, int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(basicDate);
        rightNow.add(Calendar.MONTH, +n);
        return rightNow.getTime();
    }

    // µ±Ç°ÈÕÆÚ¼Ó¼õn¸öÔÂºóµÄÈÕÆÚ£¬·µ»ØString
    public static String nMonthsAfterOneDateString(Date basicDate, int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(basicDate);
        rightNow.add(Calendar.MONTH, +n);
        return df.format(rightNow.getTime());
    }

    //    ¼ÆËãÁ½¸öÈÕÆÚÏà¸ôµÄÌìÊý
    public static int nDaysBetweenTwoDate(Date firstDate, Date secondDate) {
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) /
                (24 * 60 * 60 * 1000));
        return nDay;
    }

    // ¼ÆËãÁ½¸öÈÕÆÚÏà¸ôµÄÌìÊý
    public static int nDaysBetweenTwoDate(String firstString, String secondString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = df.parse(firstString);
            secondDate = df.parse(secondString);
        } catch (Exception e) {
            // ÈÕÆÚÐÍ×Ö·û´®¸ñÊ½´íÎó
        }

        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) /
                (24 * 60 * 60 * 1000));
        return nDay;
    }

    //µÃµ½¸ø¶¨ÈÕÆÚÊÇÐÇÆÚ¼¸£¨´ÓÐÇÆÚÌì¿ªÊ¼,1-7£©
    public static int getWeekNumOfDate(Date date) {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.
                getInstance();
        calendar.setTime(date);
        return calendar.get(GregorianCalendar.DAY_OF_WEEK);
    }

    public static int getWeekNumOfDate(String date) {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.
                getInstance();
        calendar.setTime(DateTimeUtils.toDate(date));
        return calendar.get(GregorianCalendar.DAY_OF_WEEK);
    }

    //µÃµ½¸ø¶¨ÈÕÆÚËùÔÚÐÇÆÚµÄÄ³Ò»ÌìµÄÈÕÆÚ
    //weekDay: from sunday to SATURDAY£¬1 to 7
    public static Date getWeekDayDate(int weekDay, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (weekDay >= Calendar.SUNDAY && weekDay <= Calendar.SATURDAY) {
            int delta = weekDay - c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DAY_OF_WEEK, delta);
        }
        return new Date(c.getTimeInMillis());
    }

    public static GregorianCalendar getnDayDiff(GregorianCalendar cal, int n) {
        GregorianCalendar g1 = new GregorianCalendar(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        g1.add(Calendar.DAY_OF_MONTH, +n);
        return g1;
    }

    public static GregorianCalendar getnMonthDiff(GregorianCalendar cal, int n) {
        GregorianCalendar g1 = new GregorianCalendar(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        g1.add(Calendar.MONTH, +n);
        return g1;
    }

    public static GregorianCalendar getnYearDiff(GregorianCalendar cal, int n) {
        GregorianCalendar g1 = new GregorianCalendar(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        g1.add(Calendar.YEAR, +n);
        return g1;
    }

    public static String long2DateString(String longVal) {
        if (Long.parseLong(longVal) <= 0) {
            return "";
        }
        return dateToShortString(long2Date(longVal));
    }

    public static String long2DateString(String longVal, String format) {
        if (Long.parseLong(longVal) <= 0) {
            return "";
        }
        return dateToShortString(long2Date(longVal), format);
    }

    /**
     * È¡µÃ¼ä¸ôµÄÌìÊý
     *
     * @param begin long
     * @param end   long
     * @return int
     */
    public static int getDayDiff(long begin, long end) {
        long interval = end - begin;
        if (interval > 0) {
            return (int) (interval / 3600 / 1000 / 24);
        }
        return 0;
    }

    public static int getDayDiff(String beginStr, String endStr) {
        try {
            long begin = Long.parseLong(beginStr);
            long end = Long.parseLong(endStr);
            return getDayDiff(begin, end);
        } catch (Exception e) {

        }
        return 0;
    }

    public static long getHourDiff(Date startDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
//		return day + "天" + hour + "小时" + min + "分钟";

        return day * 24 + hour;
    }

}
