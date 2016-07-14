package com.ws.apple.ayuep.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by apple on 16/7/9.
 */

@DatabaseTable(tableName = "product")
public class ProductDBModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId = true, index = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "productId")
    @SerializedName("ProductId")
    private String productId;

    @DatabaseField(columnName = "storeId")
    @SerializedName("StoreId")
    private String storeId;

    @DatabaseField(columnName = "price")
    @SerializedName("Price")
    private double price;

    @DatabaseField(columnName = "productType")
    @SerializedName("ProductType")
    private String productType;

    @DatabaseField(columnName = "productDescription")
    @SerializedName("ProductDescription")
    private String productDescription;

    @DatabaseField(columnName = "images")
    @SerializedName("Images")
    private String images;

    @DatabaseField(columnName = "updatedTime")
    @SerializedName("UpdatedTime")
    private String UpdatedTime;

    @DatabaseField(columnName = "createdTime")
    @SerializedName("CreatedTime")
    private String CreatedTime;

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

    public String getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        UpdatedTime = updatedTime;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }
}
