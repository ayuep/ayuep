package com.ws.apple.ayuep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.ConfigurationModel;
import com.ws.apple.ayuep.proxy.ConfigurationProxy;
import com.ws.apple.ayuep.proxy.DeviceProxy;
import com.ws.apple.ayuep.ui.navigation.BottomNavigationActivity;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

/*
* 设置启动的背景页面。
*/
public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new DeviceProxy().registerDevice(this, new DeviceAsyncHttpResponseHandler());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AYuePApplication.setmCurrentActivity(this);
        new ConfigurationProxy().getConfiguration(this, new ConfigrationAsyncHttpResponseHandler());
    }

    private void jumpToNavigation() {
        Intent intent = new Intent(LauncherActivity.this, BottomNavigationActivity.class);
        startActivity(intent);
        this.finish();
    }

    private class DeviceAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            Log.d("Keven", "success");
        }
    }

    private class ConfigrationAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();

                ConfigurationModel mConfiguration = gson.fromJson(response, new TypeToken<ConfigurationModel>(){}.getType());
                DataCacheManager.getDataCacheManager(LauncherActivity.this).setConfiguration(mConfiguration);
                jumpToNavigation();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
            super.onFailure(statusCode, headers, responseBytes, throwable);
        }

    }
}
