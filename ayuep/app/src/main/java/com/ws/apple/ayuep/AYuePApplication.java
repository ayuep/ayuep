package com.ws.apple.ayuep;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class AYuePApplication extends Application {

    private String TAG = AYuePApplication.class.getName();
    private static Context mApplicationContext;
    private static Activity mCurrentActivity;
    private ListActivity listActivity;

    @Override
    public void onCreate() {
        Log.d(TAG, "application onCreate");
        super.onCreate();

        mApplicationContext = getApplicationContext();
        init();
    }

    private void init() {
        // register crash handler.
        CrashHandler.getInstance();
        ImageLoaderConfiguration config = new  ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    public static Activity getmCurrentActivity() {
        return mCurrentActivity;
    }

    public static void setmCurrentActivity(Activity mCurrentActivity) {
        AYuePApplication.mCurrentActivity = mCurrentActivity;
    }

    public static Context getmApplicationContext() {
        return mApplicationContext;
    }
}
