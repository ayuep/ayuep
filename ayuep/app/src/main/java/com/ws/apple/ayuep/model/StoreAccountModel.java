package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 16/7/9.
 */

public class StoreAccountModel {

    @SerializedName("Id")
    private int id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Password")
    private String password;

    @SerializedName("InvitationCode")
    private String invitationCode;

    @SerializedName("StoreId")
    private String storeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
