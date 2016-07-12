package com.ws.apple.ayuep.util;

import android.content.Context;
import android.database.SQLException;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.ws.apple.ayuep.Constants;
import com.ws.apple.ayuep.dao.SettingModelDao;
import com.ws.apple.ayuep.entity.SettingModel;

import java.util.UUID;

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

    /**
     * Detect if there is an SDcard mounted with read/write access on the device.
     *
     * @return
     */
    public static boolean isSDcardAccessed() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
