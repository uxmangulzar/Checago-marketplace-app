package com.softarena.checagocoffee.Acitivity.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAd {
    @SerializedName("ad_id")
    @Expose
    private String adId;
    @SerializedName("ad_name")
    @Expose
    private String adName;
    @SerializedName("ad_link")
    @Expose
    private String adLink;
    @SerializedName("ad_image")
    @Expose
    private String adImage;
    @SerializedName("ad_status")
    @Expose
    private String adStatus;
    @SerializedName("ad_created_at")
    @Expose
    private String adCreatedAt;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getAdImage() {
        return adImage;
    }

    public void setAdImage(String adImage) {
        this.adImage = adImage;
    }

    public String getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(String adStatus) {
        this.adStatus = adStatus;
    }

    public String getAdCreatedAt() {
        return adCreatedAt;
    }

    public void setAdCreatedAt(String adCreatedAt) {
        this.adCreatedAt = adCreatedAt;
    }

}
