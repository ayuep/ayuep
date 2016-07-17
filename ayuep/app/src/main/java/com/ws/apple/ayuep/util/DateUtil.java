package com.ws.apple.ayuep.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

public class DateUtil {

    private static SimpleDateFormat UTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.getDefault());
    private static SimpleDateFormat JsonFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
    private static final String TIMEZONE_UTC = "UTC";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * @return like "2016-07-120T13:29:00.7592405Z"
     */
    public static String getUTCFormatTime() {
        Calendar calendar = Calendar.getInstance();
        UTCFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
        return UTCFormat.format(calendar.getTime());
    }

    public static String getDateString(Date date) {
        return dateFormat.format(date);
    }

    public static String getTimeString(Date date) {
        return timeFormat.format(date);
    }

    public static String getDateTimeString(Date date) {
        return dateFormat.format(date) + " " + timeFormat.format(date);
    }

    public static String getJsonDateString(Date date) {
        return JsonFormat.format(date);
    }

    public static Date dateFromServerString(String jsonDate) {
        try {
            return serverFormat.parse(jsonDate);
        } catch (ParseException e) {
            Log.d("Convert failed", "date");
            return new Date();
        }
    }
}
