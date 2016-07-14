package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;
import com.ws.apple.ayuep.entity.ProductDBModel;

/**
 * Created by apple on 16/7/9.
 */

public class StoreOrderModel {

    @SerializedName("Customer")
    private CustomerModel customer;

    @SerializedName("Product")
    private ProductDBModel product;

    @SerializedName("Order")
    private OrderModel order;

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public ProductDBModel getProduct() {
        return product;
    }

    public void setProduct(ProductDBModel product) {
        this.product = product;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }
}
