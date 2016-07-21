package com.ws.apple.ayuep.proxy;


import android.content.Context;

import com.google.gson.Gson;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.StoreAccountModel;
import com.ws.apple.ayuep.util.HttpUtil;

public class StoreProxy {

    private String baseUrl = BuildConfig.SERVICE_URL;

    public void getAllStores(Context context, BaseAsyncHttpResponseHandler handler) {

        String url = baseUrl + "/api/store";

        HttpUtil.get(context, url, null, handler);
    }

    public void getMyStoreWithKey(Context context, StoreAccountModel storeAccountModel, BaseAsyncHttpResponseHandler handler){
        String url = baseUrl + "/api/store";
        Gson gson = new Gson();
        HttpUtil.put(context, url, gson.toJson(storeAccountModel), handler);
    }

    public void updateStore(Context context, StoreInfoDBModel storeInfoDBModel, BaseAsyncHttpResponseHandler handler) {
        String url = baseUrl + "/api/store";
        Gson gson = new Gson();
        HttpUtil.post(context, url, gson.toJson(storeInfoDBModel), handler);
    }
}
