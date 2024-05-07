package com.softarena.checagocoffee.Acitivity.Reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopAllReview {

    @SerializedName("shop_review_id")
    @Expose
    private String shopReviewId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("shop_review_order_id")
    @Expose
    private String shopReviewOrderId;
    @SerializedName("shop_review_rating")
    @Expose
    private String shopReviewRating;
    @SerializedName("shop_review_comment")
    @Expose
    private String shopReviewComment;
    @SerializedName("shop_review_date")
    @Expose
    private String shopReviewDate;

    public String getShopReviewId() {
        return shopReviewId;
    }

    public void setShopReviewId(String shopReviewId) {
        this.shopReviewId = shopReviewId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getShopReviewOrderId() {
        return shopReviewOrderId;
    }

    public void setShopReviewOrderId(String shopReviewOrderId) {
        this.shopReviewOrderId = shopReviewOrderId;
    }

    public String getShopReviewRating() {
        return shopReviewRating;
    }

    public void setShopReviewRating(String shopReviewRating) {
        this.shopReviewRating = shopReviewRating;
    }

    public String getShopReviewComment() {
        return shopReviewComment;
    }

    public void setShopReviewComment(String shopReviewComment) {
        this.shopReviewComment = shopReviewComment;
    }

    public String getShopReviewDate() {
        return shopReviewDate;
    }

    public void setShopReviewDate(String shopReviewDate) {
        this.shopReviewDate = shopReviewDate;
    }

}
