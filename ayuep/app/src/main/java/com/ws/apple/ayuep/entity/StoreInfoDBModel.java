package com.ws.apple.ayuep.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "Location")
public class StoreInfoDBModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId = true, index = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "StoreId")
    private String storeId;

    @SerializedName("StoreName")
    private String storeName;

    @SerializedName("StoreAddress")
    private String storeAddress;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("StoreImage")
    private String storeImage;

    @SerializedName("CreatedTime")
    private String createdTime;

    @SerializedName("UpdatedTime")
    private String updatedTime;

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getId() {
        return id;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
