package com.ws.apple.ayuep.proxy;


import android.content.Context;

import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.util.HttpUtil;

public class StoreProxy {

    private String baseUrl = BuildConfig.SERVICE_URL;

    public void getAllStores(Context context, BaseAsyncHttpResponseHandler handler) {

        String url = baseUrl + "/api/store";

        HttpUtil.get(context, url, null, handler);
    }
}
