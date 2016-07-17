package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;
import com.ws.apple.ayuep.entity.ProductDBModel;

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
    private String expectedTime;

    private ProductDBModel product;

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

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public ProductDBModel getProduct() {
        return product;
    }

    public void setProduct(ProductDBModel product) {
        this.product = product;
    }
}
