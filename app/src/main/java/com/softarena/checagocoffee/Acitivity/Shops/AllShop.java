package com.softarena.checagocoffee.Acitivity.Shops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softarena.checagocoffee.Acitivity.Subcategories.AllSubcategoryResponseModel;

import java.io.Serializable;
import java.util.List;

public class AllShop implements Serializable {
    @SerializedName("shop_id")
    @Expose
    private int shopId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_description")
    @Expose
    private String shopDescription;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_lat")
    @Expose
    private String shopLat;
    @SerializedName("shop_lng")
    @Expose
    private String shopLng;
    @SerializedName("shop_billing_address")
    @Expose
    private String shopBillingAddress;
    @SerializedName("shop_image")
    @Expose
    private String shopImage;
    @SerializedName("shop_created_at")
    @Expose
    private String shopCreatedAt;

    @SerializedName("shop_total_reviews")
    @Expose
    private String shop_total_reviews;
    @SerializedName("shop_total_rating")
    @Expose
    private String shop_total_rating;

    @SerializedName("shop_distance")
    @Expose
    private String shop_distance;

    @SerializedName("shop_status")
    @Expose
    private String shopStatus;
    @SerializedName("shop_all_subcats")
    @Expose
    private List<AllSubcategoryResponseModel> shopAllSubcats = null;
    @SerializedName("shop_timing")
    @Expose
    private List<ShopTimingResponseModel> shopTiming = null;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getShopLng() {
        return shopLng;
    }

    public void setShopLng(String shopLng) {
        this.shopLng = shopLng;
    }

    public Object getShopBillingAddress() {
        return shopBillingAddress;
    }

    public void setShopBillingAddress(String shopBillingAddress) {
        this.shopBillingAddress = shopBillingAddress;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopCreatedAt() {
        return shopCreatedAt;
    }

    public void setShopCreatedAt(String shopCreatedAt) {
        this.shopCreatedAt = shopCreatedAt;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }


    public String getShop_total_reviews() {
        return shop_total_reviews;
    }

    public void setShop_total_reviews(String shop_total_reviews) {
        this.shop_total_reviews = shop_total_reviews;
    }

    public String getShop_total_rating() {
        return shop_total_rating;
    }

    public void setShop_total_rating(String shop_total_rating) {
        this.shop_total_rating = shop_total_rating;
    }

    public String getShop_distance() {
        return shop_distance;
    }

    public void setShop_distance(String shop_distance) {
        this.shop_distance = shop_distance;
    }

    public List<AllSubcategoryResponseModel> getShopAllSubcats() {
        return shopAllSubcats;
    }

    public void setShopAllSubcats(List<AllSubcategoryResponseModel> shopAllSubcats) {
        this.shopAllSubcats = shopAllSubcats;
    }

    public List<ShopTimingResponseModel> getShopTiming() {
        return shopTiming;
    }

    public void setShopTiming(List<ShopTimingResponseModel> shopTiming) {
        this.shopTiming = shopTiming;
    }
}
