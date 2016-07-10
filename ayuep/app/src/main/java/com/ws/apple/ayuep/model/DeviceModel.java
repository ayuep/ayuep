package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 16/7/9.
 */

public class DeviceModel {

    @SerializedName("DeviceToken")
    private String deviceToken;

    @SerializedName("DeviceIdentity")
    private String deviceIdentity;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceIdentity() {
        return deviceIdentity;
    }

    public void setDeviceIdentity(String deviceIdentity) {
        this.deviceIdentity = deviceIdentity;
    }
}
