package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by apple on 16/7/9.
 */

public class ProductModel {

    @SerializedName("ProductId")
    private String productId;

    @SerializedName("StoreId")
    private String storeId;

    @SerializedName("Price")
    private double price;

    @SerializedName("ProductType")
    private String productType;

    @SerializedName("ProductDescription")
    private String productDescription;

    @SerializedName("Images")
    private String images;

    @SerializedName("UpdatedTime")
    private Date UpdatedTime;

    @SerializedName("CreatedTime")
    private Date CreatedTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        UpdatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(Date createdTime) {
        CreatedTime = createdTime;
    }
}
