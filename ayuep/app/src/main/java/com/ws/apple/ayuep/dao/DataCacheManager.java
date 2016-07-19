package com.ws.apple.ayuep.dao;

import android.content.Context;
import android.database.SQLException;
import android.text.TextUtils;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.ws.apple.ayuep.Constants;
import com.ws.apple.ayuep.entity.SettingModel;

import java.util.UUID;

/**
 * Created by apple on 16/7/12.
 */

public class DataCacheManager {

    private static DataCacheManager instance;

    private String deviceIdentity;

    private String storeId;
    
    public static DataCacheManager getDataCacheManager(Context context) {
        if (instance == null) {
            synchronized (DataCacheManager.class) {
                if (instance == null) {
                    instance = new DataCacheManager();
                    instance.setDeviceIdentity(context);
                }
            }
        }
        return instance;
    }

    public String getDeviceIdentity() {
        return deviceIdentity;
    }

    private void setDeviceIdentity(Context context) {
        String deviceIdentity = null;
        SettingModelDao dao = new SettingModelDao(context);
        try {
            SettingModel deviceIdentitySetting = dao.query(Constants.SettingKeyDeviceIdentity);
            if (deviceIdentitySetting != null) {
                deviceIdentity = deviceIdentitySetting.getValue();
            }
        } catch (java.sql.SQLException e) {
            Log.d("AYueP", e.getMessage());
        }

        if (deviceIdentity == null) {
            deviceIdentity = UUID.randomUUID().toString();
            SettingModel deviceIdentitySetting = new SettingModel();
            deviceIdentitySetting.setKey(Constants.SettingKeyDeviceIdentity);
            deviceIdentitySetting.setValue(deviceIdentity);

            try {
                dao.insert(deviceIdentitySetting);
            } catch (SQLException e) {
                Log.d("AYueP", e.getMessage());
            }
        }

        this.deviceIdentity = deviceIdentity;
    }


    public String getStoreId(Context context) {
        if (TextUtils.isEmpty(this.storeId)) {
            try {
                SettingModel storeIdSetting = new SettingModelDao(context).query(Constants.SettingKeyCurrentStoreId);
                if (storeIdSetting != null) {
                    this.storeId = storeIdSetting.getValue();
                }
            } catch (java.sql.SQLException e) {
                Log.d("AYueP", e.getMessage());
            }
        }
        return storeId;
    }

    public void setStoreId(Context context, String storeId) {
        this.storeId = storeId;
        SettingModel settingModel = new SettingModel();
        settingModel.setKey(Constants.SettingKeyCurrentStoreId);
        settingModel.setValue(storeId);
        new SettingModelDao(context).insert(settingModel);
    }
}
