package com.ws.apple.ayuep.handler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;
import com.ws.apple.ayuep.AYuePApplication;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;

public abstract class BaseAsyncHttpResponseHandler extends TextHttpResponseHandler {

    private String TAG = BaseAsyncHttpResponseHandler.class.getName();
    protected Context mContext;

    public BaseAsyncHttpResponseHandler() {
        if (mContext == null) {
            mContext = AYuePApplication.getmCurrentActivity() == null ? AYuePApplication.getmApplicationContext() : AYuePApplication.getmCurrentActivity();
        }
    }

    public BaseAsyncHttpResponseHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String response) {
        if (statusCode == HttpStatus.SC_OK && response != null) {

            // 如何当前的context 是activity， 并且正在被销毁。
            if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
                onSuccessWithoutUI(response);
                return;
            }
            onSuccess(response);
        }
    }

    public void onSuccessWithoutUI(String response) {
    }

    public abstract void onSuccess(String response);

    @Override
    public final void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
        Log.e(TAG, e.getMessage());
    }
}
