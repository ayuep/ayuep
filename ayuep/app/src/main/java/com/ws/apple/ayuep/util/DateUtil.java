package com.ws.apple.ayuep.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static SimpleDateFormat UTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.getDefault());
    private static final String TIMEZONE_UTC = "UTC";

    /**
     * @return like "2016-07-120T13:29:00.7592405Z"
     */
    public static String getUTCFormatTime() {
        Calendar calendar = Calendar.getInstance();
        UTCFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
        return UTCFormat.format(calendar.getTime());
    }





}
