package com.ws.apple.ayuep.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean isPhoneNumberValid(String PhoneNumber ){
        boolean flag = false;
        String regex = "[1][358]\\d{9}";
        CharSequence inputStr = PhoneNumber;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches() ) {
            flag = true;
        }

        return flag;
    }
}
