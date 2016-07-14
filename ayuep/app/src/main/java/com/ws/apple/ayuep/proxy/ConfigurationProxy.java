package com.ws.apple.ayuep.proxy;

import android.content.Context;

import com.google.gson.Gson;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.DeviceModel;
import com.ws.apple.ayuep.util.HttpUtil;

/**
 * Created by apple on 16/7/14.
 */

public class ConfigurationProxy {

    public void getConfiguration(Context context, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/configuration";

        HttpUtil.get(context, url, null, handler);
    }
}
