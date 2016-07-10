package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by apple on 16/7/9.
 */

public class OrderModel {

    @SerializedName("OrderId")
    private String orderId;

    @SerializedName("ProductId")
    private  String productId;

    @SerializedName("CustomerId")
    private  String customerId;

    @SerializedName("Status")
    private String status;

    @SerializedName("ExpectedTime")
    private Date expectedTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Date expectedTime) {
        this.expectedTime = expectedTime;
    }
}
