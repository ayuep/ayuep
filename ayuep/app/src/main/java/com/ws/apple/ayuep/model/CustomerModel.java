package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by apple on 16/7/9.
 */

public class CustomerModel implements Serializable {

    @SerializedName("CustomerId")
    private String customerId;

    @SerializedName("CustomerName")
    private String customerName;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("Address")
    private String address;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
