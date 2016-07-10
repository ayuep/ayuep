package com.ws.apple.ayuep;

import android.app.Application;
import android.app.ListActivity;
import android.util.Log;

public class AYuePApplication extends Application {

    private ListActivity listActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        // register crash handler.
        CrashHandler.getInstance();
    }
}
