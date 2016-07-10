package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 16/7/9.
 */

public class RotationModel {

    @SerializedName("Id")
    private int id;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("ProductId")
    private String productId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
