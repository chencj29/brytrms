/**
 * DateTimeConverter.java
 * com.jfly.core.view
 * Copyright (c) 2014.
 */

package com.thinkgem.jeesite.common.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

/**
 * 日期转换
 * <p>
 * format [yyyy-MM-dd] format [yyyy-MM-dd HH:mm:ss] format [yyyy-MM-dd
 * HH:mm:ss.SSS]
 *
 * @author yangjian1004
 * @Date Aug 4, 2014
 */
@SuppressWarnings({ "rawtypes" })
public class DateTimeConverter implements Converter {

	private static Logger log = Logger.getLogger(DateTimeConverter.class);

	private static final String TIME1 = "HHmm";
	private static final String TIME2 = "HH:mm";
	private static final String DATE = "yyyy-MM-dd";
	private static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	public DateTimeConverter() {
		super();
	}

	@Override
	public Object convert(Class type, Object value) {
		return toDate(type, value);
	}

	public static Object toDate(Class type, Object value) {
		try {
			if (value == null || "".equals(value))
				return null;
			if (value instanceof String) {
				String dateValue = value.toString().trim();
				int length = dateValue.length();
				if (type.equals(java.util.Date.class)) {
					DateFormat formatter = null;
					if (length < 4) {
						return null;
					}
					if (length == 4) {
						Date date = null;
						if (dateValue.indexOf(":") == -1) {
							formatter = new SimpleDateFormat(TIME1, new DateFormatSymbols(Locale.CHINA));
							date = formatter.parse(dateValue);
						} else {
							date = null;
						}
						return date;
					}
					if (length == 5) {
						formatter = new SimpleDateFormat(TIME2, new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
					if (length <= 10) {
						formatter = new SimpleDateFormat(DATE, new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
					if (length <= 19) {
						formatter = new SimpleDateFormat(DATETIME, new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
					if (length <= 23) {
						formatter = new SimpleDateFormat(TIMESTAMP, new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
				}
			}
		} catch (ParseException e) {
			value = null;
			log.error("日期格式无法解析，返回Null...", e);
		}
		return value;
	}
}