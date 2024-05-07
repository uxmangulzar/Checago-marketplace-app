package com.softarena.checagocoffee.Acitivity.Shops;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopTimingResponseModel implements Serializable
{

    @SerializedName("shop_time_id")
    @Expose
    private String shopTimeId;
    @SerializedName("shop_time_day")
    @Expose
    private String shopTimeDay;
    @SerializedName("shop_time_opening")
    @Expose
    private String shopTimeOpening;
    @SerializedName("shop_time_closing")
    @Expose
    private String shopTimeClosing;
    private final static long serialVersionUID = 14672829160729713L;

    public String getShopTimeId() {
        return shopTimeId;
    }

    public void setShopTimeId(String shopTimeId) {
        this.shopTimeId = shopTimeId;
    }

    public String getShopTimeDay() {
        return shopTimeDay;
    }

    public void setShopTimeDay(String shopTimeDay) {
        this.shopTimeDay = shopTimeDay;
    }

    public String getShopTimeOpening() {
        return shopTimeOpening;
    }

    public void setShopTimeOpening(String shopTimeOpening) {
        this.shopTimeOpening = shopTimeOpening;
    }

    public String getShopTimeClosing() {
        return shopTimeClosing;
    }

    public void setShopTimeClosing(String shopTimeClosing) {
        this.shopTimeClosing = shopTimeClosing;
    }

}