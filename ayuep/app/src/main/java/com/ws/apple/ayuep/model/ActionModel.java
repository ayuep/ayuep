package com.ws.apple.ayuep.model;

import java.io.Serializable;

/**
 * Created by apple on 16/7/15.
 */

public class ActionModel implements Serializable {

    private NavigatorType navigatorType;

    private String productType;

    private String storeId;

    private String productId;

    public NavigatorType getNavigatorType() {
        return navigatorType;
    }

    public void setNavigatorType(NavigatorType navigatorType) {
        this.navigatorType = navigatorType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
