package com.ws.apple.ayuep.proxy;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.util.HttpUtil;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by apple on 16/7/19.
 */

public class FileUploadProxy {

    public void upload(Context context, RequestParams entity, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/FileUpload";

        HttpUtil.post(context, url, entity, handler);
    }
}
