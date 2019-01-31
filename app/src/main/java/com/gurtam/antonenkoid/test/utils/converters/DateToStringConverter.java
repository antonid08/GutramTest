package com.gurtam.antonenkoid.test.utils.converters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateToStringConverter implements Converter<Date, String> {

    /**
     * yyyy-MM-dd
     */
    public static final String ISO_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * dd.MM.yyyy
     */
    public static final String RU_LOCALE_DATE_PATTERN = "dd.MM.yyyy";

    /**
     * HH:mm:ss
     */
    public static final String RU_LOCALE_TIME_PATTERN = "HH:mm:ss";

    /**
     * dd.MM.yyyy hh:mm:ss
     */
    public static final String RU_LOCALE_DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";

    /**
     * dd MMMM
     */
    public static final String RU_LOCALE_SHORT_PATTERN = "dd MMMM";

    /**
     * MM.yyyy
     */
    public static final String RU_LOCALE_MONTH_PATTERN = "MM.yyyy";

    /**
     * ru локаль dd.MM.yyyy
     */
    public static final String RU_LOCALE_CARD_PATTERN = RU_LOCALE_DATE_PATTERN;

    private static final String NULL_DATE_STRING = "";

    private final String format;
    private final Locale locale;

    public DateToStringConverter(String format, Locale locale) {
        if (format == null) {
            throw new IllegalArgumentException("Can't create DateToString converter with null format!");
        }
        if (locale == null) {
            throw new IllegalArgumentException("Can't create DateToString converter with null locale!");
        }

        this.format = format;
        this.locale = locale;
    }

    public DateToStringConverter(String format) {
        this(format, new Locale("RU"));
    }

    public String convert(Date input) {
        return input == null ? NULL_DATE_STRING : new SimpleDateFormat(format, locale).format(input);
    }
}
