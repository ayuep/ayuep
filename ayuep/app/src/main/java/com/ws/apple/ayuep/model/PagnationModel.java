package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 16/7/9.
 */

public class PagnationModel {

    @SerializedName("Page")
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
