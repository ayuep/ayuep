package com.ws.apple.ayuep.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpHeaderUtil {

    private static final String REQUEST_TIME = "RequestTime";
    private static final String APP_PLATFORM = "AppPlatform";
    private static final String REQUEST_ID = "requestId";
    private static final String APP_ANDROID = "ANDROID";

    /**
     * Generate request header. These request header is the same when calling service.
     *
     * @return the map of request header
     */
    public static Map<String, String> generateRequestHeader() {
        Map<String, String> requestHeader = new HashMap<String, String>();
        UUID uuid = UUID.randomUUID();

        requestHeader.put(REQUEST_ID, uuid.toString());
        requestHeader.put(APP_PLATFORM, APP_ANDROID);
        requestHeader.put(REQUEST_TIME, getRealTimeForRequestHeader());

        return requestHeader;
    }

    private static String getRealTimeForRequestHeader() {
        return DateUtil.getUTCFormatTime();
    }
}
