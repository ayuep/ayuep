package com.ws.apple.ayuep.proxy;

import android.content.Context;

import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.util.HttpUtil;

/**
 * Created by apple on 16/7/14.
 */

public class ProductProxy {

    private String baseUrl = BuildConfig.SERVICE_URL;

    public void getProducts(Context context, BaseAsyncHttpResponseHandler handler) {

        String url = baseUrl + "/api/products";

        HttpUtil.get(context, url, null, handler);
    }

    public void getProductSales(Context context, String productId, BaseAsyncHttpResponseHandler handler) {
        String url = baseUrl + "/api/products?productId=" + productId;

        HttpUtil.get(context, url, null, handler);
    }
}
