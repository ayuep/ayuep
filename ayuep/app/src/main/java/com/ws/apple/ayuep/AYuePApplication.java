package com.ws.apple.ayuep;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Locale;

import static com.nostra13.universalimageloader.core.assist.ImageScaleType.EXACTLY_STRETCHED;
import static com.nostra13.universalimageloader.core.assist.ImageScaleType.NONE_SAFE;

public class AYuePApplication extends Application {

    private String TAG = AYuePApplication.class.getName();
    private static Context mApplicationContext;
    private static Activity mCurrentActivity;
    private ListActivity listActivity;
    private DisplayImageOptions mDisplayImageOptions;

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
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(EXACTLY_STRETCHED)
                .build();
        ImageLoaderConfiguration config = new  ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(displayImageOptions)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(config);

        setLang(Locale.SIMPLIFIED_CHINESE);

    }

    private void setLang(Locale l) {
        // 获得res资源对象
        Resources resources = getResources();
        // 获得设置对象
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 语言
        config.setLocale(l);
        resources.updateConfiguration(config, dm);
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
