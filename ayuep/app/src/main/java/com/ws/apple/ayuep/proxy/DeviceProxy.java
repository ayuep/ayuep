package com.ws.apple.ayuep.proxy;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.ws.apple.ayuep.AYuePApplication;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.DeviceModel;
import com.ws.apple.ayuep.util.DeviceUtil;
import com.ws.apple.ayuep.util.HttpHeaderUtil;
import com.ws.apple.ayuep.util.HttpUtil;

import cz.msebera.android.httpclient.Header;

public class DeviceProxy {

    public void registerDevice(Context context, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/device";

        DeviceModel device = new DeviceModel();
        device.setDeviceIdentity(DataCacheManager.getDataCacheManager(context).getDeviceIdentity());
        device.setDeviceType("Android");
        Gson gson = new Gson();

        HttpUtil.post(context, url, gson.toJson(device), handler);
    }

}
