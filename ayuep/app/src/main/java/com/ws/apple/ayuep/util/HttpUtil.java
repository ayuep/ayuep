package com.ws.apple.ayuep.util;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;
import java.util.Set;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtil {

    private static String TAG = HttpUtil.class.getName();
    private final static int MAX_TOTAL_CONNECTIONS = 30;
    private final static int CONNECT_TIMEOUT = 20 * 1000;
    private final static String CONTENT_TYPE = "application/json";

    private static AsyncHttpClient httpClient;

    private static AsyncHttpClient getAsyncHttpClient(Context context) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException {
        if (null == httpClient) {
            httpClient = new AsyncHttpClient();
            httpClient.setMaxConnections(MAX_TOTAL_CONNECTIONS);
            httpClient.setTimeout(CONNECT_TIMEOUT);
            httpClient.setURLEncodingEnabled(true);
        }
        // add parameter of Request Header
        addHeader(HttpHeaderUtil.generateRequestHeader());

        return httpClient;
    }

    private static void addHeader(Map<String, String> requestHeaders) {
        if (null != requestHeaders && requestHeaders.size() > 0) {
            Set<String> headerKeys = requestHeaders.keySet();
            for (String headerKey : headerKeys) {
                String headerValue = requestHeaders.get(headerKey);
                httpClient.addHeader(headerKey, headerValue);
            }
        }
    }

    public static void post(Context context, String url, String jsonParams, BaseAsyncHttpResponseHandler responseHandler) {
        try {
            HttpEntity entity = new StringEntity(jsonParams, "utf-8");
            getAsyncHttpClient(context).post(context, url, entity, CONTENT_TYPE, responseHandler);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static void get(Context context, String url, RequestParams params, TextHttpResponseHandler responseHandler) {
        try {
            getAsyncHttpClient(context).get(context, url, params, responseHandler);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
