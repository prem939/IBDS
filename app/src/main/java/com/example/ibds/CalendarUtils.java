package com.example.ibds;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarUtils {
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_TIME_STD_PATTERN_On = "dd/MM/yy HH:mm";
    public static final String TIME_STD_PATTERN = "HH:mm";
    public static String getCurrentDateTime() {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_STD_PATTERN_On, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }
    public static String getTime() {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_STD_PATTERN_On, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }
}
