package com.ws.apple.ayuep.util;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceUtil {

    public static int getScreenXSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        return width;
    }

    public static int getScreenYSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        return height;
    }

    public static String getDeviceIdentity(Context context) {
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // DeviceId of AVD is null, please add different suffix to 'AVD_' in local code.
        return telephonyMgr.getDeviceId() != null ? telephonyMgr.getDeviceId() : "AVD_";
    }

    /**
     * Detect if there is an SDcard mounted with read/write access on the device.
     *
     * @return
     */
    public static boolean isSDcardAccessed() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
