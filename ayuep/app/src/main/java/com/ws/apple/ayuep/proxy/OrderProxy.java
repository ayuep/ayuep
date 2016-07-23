package com.ws.apple.ayuep.proxy;

import android.content.Context;

import com.google.gson.Gson;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.OrderModel;
import com.ws.apple.ayuep.util.HttpUtil;

/**
 * Created by apple on 16/7/17.
 */

public class OrderProxy {

    public void order(Context context, OrderModel order, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/order";

        Gson gson = new Gson();
        HttpUtil.post(context, url, gson.toJson(order), handler);
    }

    public void getMyOrders(Context context, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/order";

        HttpUtil.get(context, url, null, handler);
    }

    public void getStoreOrders(Context context, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/storeorder";

        HttpUtil.get(context, url, null, handler);
    }

    public void updateOrderStatus(Context context, OrderModel order, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/StoreOrder";

        Gson gson = new Gson();

        HttpUtil.put(context, url, gson.toJson(order), handler);
    }
}