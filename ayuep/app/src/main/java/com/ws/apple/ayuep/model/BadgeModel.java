package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 16/7/9.
 */

public class BadgeModel {

    @SerializedName("Count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
