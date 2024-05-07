package com.softarena.checagocoffee.Acitivity.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllNews implements Serializable {

    @SerializedName("news_id")
    @Expose
    private String newsId;
    @SerializedName("news_name")
    @Expose
    private String newsName;
    @SerializedName("news_link")
    @Expose
    private String newsLink;
    @SerializedName("news_image")
    @Expose
    private String newsImage;
    @SerializedName("news_status")
    @Expose
    private String newsStatus;
    @SerializedName("news_created_at")
    @Expose
    private String newsCreatedAt;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }

    public String getNewsCreatedAt() {
        return newsCreatedAt;
    }

    public void setNewsCreatedAt(String newsCreatedAt) {
        this.newsCreatedAt = newsCreatedAt;
    }

}
