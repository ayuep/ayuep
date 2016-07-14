package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apple on 16/7/14.
 */

public class ConfigurationModel {

    @SerializedName("productType")
    private List<ProductTypeModel> productTypes;

    @SerializedName("rotations")
    private List<RotationModel> banners;

    public List<ProductTypeModel> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductTypeModel> productTypes) {
        this.productTypes = productTypes;
    }

    public List<RotationModel> getBanners() {
        return banners;
    }

    public void setBanners(List<RotationModel> banners) {
        this.banners = banners;
    }
}
