package com.ws.apple.ayuep.util;

/**
 * Created by apple on 16/7/17.
 */

public class StringUtil {

    public static String getOrderStatusString (String status) {
        if (status.equals("NOTRECEIVED")) {
            return "店家还未接单";
        } else if (status.equals("RECEIVED")) {
            return "店家已接单";
        } else if (status.equals("WAITINGFORCOMMENTS")) {
            return "待评价";
        }

        return "";
    }
}
