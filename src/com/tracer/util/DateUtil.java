/**
 * @author Jp
 *
 */
package com.tracer.util;

import sun.text.resources.LocaleData;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.HashMap;


public class DateUtil {

	protected static String datePattern = "MM/dd/yyyy";
	@SuppressWarnings("rawtypes")
	protected static HashMap patterns = new HashMap();


    //~ Methods ================================================================

    /**
     * Formats given date according to specified locale and date style
     *
     * @param date      Date to convert
     * @param locale    Locale to use for formatting date
     * @param dateStyle Date style
     * @return String representation of date according to given locale and date style
     * @see java.text.DateFormat
     */
    public static final String formatDate(Date date, Locale locale, int dateStyle) {
        DateFormat formatter = DateFormat.getDateInstance(dateStyle, locale);
        return formatter.format(date);
    }

    /**
     * Formats given date according to specified locale and <code>DateFormat.MEDIUM</code> style
     *
     * @param date   Date to convert
     * @param locale Locale to use for formatting date
     * @return String representation of date according to given locale and <code>DateFormat.MEDIUM</code> style
     * @see java.text.DateFormat
     * @see java.text.DateFormat#MEDIUM
     */
    public static final String formatDate(Date date, Locale locale) {
        return formatDate(date, locale, DateFormat.MEDIUM);
    }

    /**
     * Parses given string according to specified locale and date style
     *
     * @param source    Source string to parse date from
     * @param locale    Locale to use for parsing date
     * @param dateStyle Date style
     * @return Date object corresponding to representation given in source string
     * @throws ParseException if given string could not be properly parsed according to given locale and style
     * @see java.text.DateFormat
     */
    public static final Date parseDate(String source, Locale locale, int dateStyle) throws ParseException {
        DateFormat formatter = DateFormat.getDateInstance(dateStyle, locale);
        return formatter.parse(source);
    }

    /**
     * Parses given string according to specified locale and <code>DateFormat.MEDIUM</code> style
     *
     * @param source Source string to parse date from
     * @param locale Locale to use for parsing date
     * @return Date object corresponding to representation given in source string
     * @throws ParseException if given string could not be properly parsed according to given locale and <code>DateFormat.MEDIUM</code> style
     * @see java.text.DateFormat
     * @see java.text.DateFormat#MEDIUM
     */
    public static final Date parseDate(String source, Locale locale) throws ParseException {
        return parseDate(source, locale, DateFormat.MEDIUM);
    }


    /**
     * Formats given time according to specified locale and time style
     *
     * @param time      Time to convert
     * @param locale    Locale to use for formatting time
     * @param timeStyle Time style
     * @return String representation of time according to given locale and time style
     * @see java.text.DateFormat
     */
    public static final String formatTime(Date time, Locale locale, int timeStyle) {
        DateFormat formatter = DateFormat.getTimeInstance(timeStyle, locale);
        return formatter.format(time);
    }

    /**
     * Formats given time according to specified locale and <code>DateFormat.MEDIUM</code> style
     *
     * @param time   Time to convert
     * @param locale Locale to use for formatting time
     * @return String representation of time according to given locale and <code>DateFormat.MEDIUM</code> style
     * @see java.text.DateFormat
     * @see java.text.DateFormat#MEDIUM
     */
    public static final String formatTime(Date time, Locale locale) {
        return formatTime(time, locale, DateFormat.MEDIUM);
    }

    /**
     * Parses given string according to specified locale and time style
     *
     * @param source    Source string to parse time from
     * @param locale    Locale to use for parsing time
     * @param timeStyle Time style
     * @return Time object corresponding to representation given in source string
     * @throws ParseException if given string could not be properly parsed according to given locale and style
     * @see java.text.DateFormat
     */
    public static final Date parseTime(String source, Locale locale, int timeStyle) throws ParseException {
        DateFormat formatter = DateFormat.getTimeInstance(timeStyle, locale);
        return formatter.parse(source);
    }

    /**
     * Parses given string according to specified locale and <code>DateFormat.MEDIUM</code> style
     *
     * @param source Source string to parse time from
     * @param locale Locale to use for parsing time
     * @return Time object corresponding to representation given in source string
     * @throws ParseException if given string could not be properly parsed according to given locale and <code>DateFormat.MEDIUM</code> style
     * @see java.text.DateFormat
     * @see java.text.DateFormat#MEDIUM
     */
    public static final Date parseTime(String source, Locale locale) throws ParseException {
        return parseTime(source, locale, DateFormat.MEDIUM);
    }

    /**
     * Formats given date and time according to specified locale and date style
     *
     * @param date      Date object to convert
     * @param locale    Locale to use for formatting date and time
     * @param dateStyle Date style
     * @param timeStyle Time style
     * @return String representation of date and time according to given locale and date style
     * @see java.text.DateFormat
     */
    public static final String formatDateTime(Date date, Locale locale, int dateStyle, int timeStyle) {
        DateFormat formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
        return formatter.format(date);
    }

    /**
     * Formats given date and time according to specified locale and <code>DateFormat.MEDIUM</code> style
     *
     * @param date   Date object to convert
     * @param locale Locale to use for formatting date and time
     * @return String representation of date and time according to given locale and <code>DateFormat.MEDIUM</code> style
     * @see java.text.DateFormat
     * @see java.text.DateFormat#MEDIUM
     */
    public static final String formatDateTime(Date date, Locale locale) {
        return formatDateTime(date, locale, DateFormat.MEDIUM, DateFormat.MEDIUM);
    }

    /**
     * Parses given string according to specified locale and date and time styles
     *
     * @param source    Source string to parse date and time from
     * @param locale    Locale to use for parsing date and time
     * @param dateStyle Date style
     * @param timeStyle Time style
     * @return Date object corresponding to representation given in source string
     * @throws ParseException if given string could not be properly parsed according to given locale and style
     * @see java.text.DateFormat
     */
    public static final Date parseDateTime(String source, Locale locale, int dateStyle, int timeStyle) throws ParseException {
        DateFormat formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
        return formatter.parse(source);
    }

    /**
     * Parses given string according to specified locale and <code>DateFormat.MEDIUM</code> style
     *
     * @param source Source string to parse date and time from
     * @param locale Locale to use for parsing date and time
     * @return Date object corresponding to representation given in source string
     * @throws ParseException if given string could not be properly parsed according to given locale and <code>DateFormat.MEDIUM</code> style
     * @see java.text.DateFormat
     * @see java.text.DateFormat#MEDIUM
     */
    public static final Date parseDateTime(String source, Locale locale) throws ParseException {
        return parseDateTime(source, locale, DateFormat.MEDIUM, DateFormat.MEDIUM);
    }

    /**
     * Returns default datePattern (MM/dd/yyyy)
     *
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /**
     * Gets date pattern without time symbols according to given locale and date style
     *
     * @param locale    Locale to searh pattern for
     * @param dateStyle Date style to search pattern by
     * @return Date pattern without time symbols
     */
    @SuppressWarnings("unchecked")
	public static String getDatePattern(Locale locale, int dateStyle) {
        String[] dateTimePatterns = (String[])patterns.get(locale);
        if (dateTimePatterns == null) {
            ResourceBundle r = LocaleData.getLocaleElements(locale);
            dateTimePatterns = r.getStringArray("DateTimePatterns");
            patterns.put(locale, dateTimePatterns);
        }
        return dateTimePatterns[dateStyle + 4];
    }

    /**
     * Gets date pattern without time symbols according to given locale in MEDIUM style
     *
     * @param locale Locale to searh pattern for
     * @return Date pattern without time symbols in medium format
     * @see java.text.DateFormat#MEDIUM
     */
    public static String getDatePattern(Locale locale) {
        return getDatePattern(locale, DateFormat.MEDIUM);
    }


}
