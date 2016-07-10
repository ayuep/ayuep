package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by apple on 16/7/9.
 */

public class StoreModel {

    @SerializedName("StoreId")
    private String storeId;

    @SerializedName("StoreName")
    private String storeName;

    @SerializedName("StoreAddress")
    private String storeAddress;

    @SerializedName("StoreImage")
    private String storeImage;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("UpdatedTime")
    private Date updatedTime;

    @SerializedName("CreatedTime")
    private Date createdTime;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
