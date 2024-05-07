package com.softarena.checagocoffee.Acitivity.Shops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softarena.checagocoffee.Acitivity.Orders.OrderResponsemodel;
import com.softarena.checagocoffee.Acitivity.Reviews.ShopAllReview;
import com.softarena.checagocoffee.Acitivity.card.AllCardsModel;

import java.util.List;

public class ShopResponseModel {
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("totalRecords")
    @Expose
    private int totalRecords;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;

    @SerializedName("all_shops")
    @Expose
    private List<AllShop> allShops = null;
    @SerializedName("user_cards")
    @Expose
    private List<AllCardsModel> user_cards = null;
    @SerializedName("shop_all_reviews")
    @Expose
    private List<ShopAllReview> shopAllReviews = null;

    @SerializedName("shop_details")
    @Expose
    private List<AllShop> shopDetails = null;

    @SerializedName("all_customer_orders")
    @Expose
    private List<OrderResponsemodel> all_customer_orders = null;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<AllShop> getAllShops() {
        return allShops;
    }

    public void setAllShops(List<AllShop> allShops) {
        this.allShops = allShops;
    }

    public List<ShopAllReview> getShopAllReviews() {
        return shopAllReviews;
    }

    public void setShopAllReviews(List<ShopAllReview> shopAllReviews) {
        this.shopAllReviews = shopAllReviews;
    }

    public List<AllShop> getShopDetails() {
        return shopDetails;
    }

    public void setShopDetails(List<AllShop> shopDetails) {
        this.shopDetails = shopDetails;
    }

    public List<OrderResponsemodel> getAll_customer_orders() {
        return all_customer_orders;
    }

    public void setAll_customer_orders(List<OrderResponsemodel> all_customer_orders) {
        this.all_customer_orders = all_customer_orders;
    }

    public List<AllCardsModel> getUser_cards() {
        return user_cards;
    }

    public void setUser_cards(List<AllCardsModel> user_cards) {
        this.user_cards = user_cards;
    }
}
