package com.softarena.checagocoffee.Acitivity.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softarena.checagocoffee.Acitivity.Category.AllCategory;

import java.util.List;

public class HomeData {
    @SerializedName("all_ads")
    @Expose
    private List<AllAd> allAds = null;
    @SerializedName("user_ship_address")
    @Expose
    private String user_ship_address = null;
    @SerializedName("all_categories")
    @Expose
    private List<AllCategory> allCategories = null;
    @SerializedName("all_news")
    @Expose
    private List<AllNews> allNews = null;


    public List<AllAd> getAllAds() {
        return allAds;
    }

    public void setAllAds(List<AllAd> allAds) {
        this.allAds = allAds;
    }

    public List<AllCategory> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<AllCategory> allCategories) {
        this.allCategories = allCategories;
    }

    public List<AllNews> getAllNews() {
        return allNews;
    }

    public void setAllNews(List<AllNews> allNews) {
        this.allNews = allNews;
    }

    public String getUser_ship_address() {
        return user_ship_address;
    }

    public void setUser_ship_address(String user_ship_address) {
        this.user_ship_address = user_ship_address;
    }
}
