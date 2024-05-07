package com.softarena.checagocoffee.Acitivity.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeMainResponsedata {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("user_status")
    @Expose
    private String user_status;
    @SerializedName("home_screen_data")
    @Expose
    private List<HomeData> homeScreenData = null;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<HomeData> getHomeScreenData() {
        return homeScreenData;
    }

    public void setHomeScreenData(List<HomeData> homeScreenData) {
        this.homeScreenData = homeScreenData;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }
}
