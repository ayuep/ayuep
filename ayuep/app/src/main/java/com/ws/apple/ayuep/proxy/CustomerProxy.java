package com.ws.apple.ayuep.proxy;

import android.content.Context;

import com.google.gson.Gson;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.CustomerModel;
import com.ws.apple.ayuep.util.HttpUtil;

import java.util.List;

/**
 * Created by Smith on 2016/7/16.
 */
public class CustomerProxy {
    private String baseUrl = BuildConfig.SERVICE_URL;

    public void getCustomer(Context context, BaseAsyncHttpResponseHandler handler) {
        String url = baseUrl + "/api/customer";
        HttpUtil.get(context, url, null, handler);
    }
    public void registerCustomer(Context context,CustomerModel customerModel ,BaseAsyncHttpResponseHandler handler) {
        String url = baseUrl + "/api/customer";

//        costomer.setDeviceIdentity(DataCacheManager.getDataCacheManager(context).getDeviceIdentity());
//        costomer.setDeviceType("Android");
        Gson gson = new Gson();
        HttpUtil.post(context, url, gson.toJson(customerModel), handler);
    }
}
