package com.softarena.checagocoffee.Acitivity.Orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResponsemodel {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_customer_id")
    @Expose
    private String orderCustomerId;
    @SerializedName("order_shop_id")
    @Expose
    private String orderShopId;
    @SerializedName("order_tracking_id")
    @Expose
    private String order_tracking_id;
    @SerializedName("order_label_id")
    @Expose
    private String order_label_id;
    @SerializedName("order_tax")
    @Expose
    private String order_tax;
    @SerializedName("order_rejection")
    @Expose
    private String order_rejection;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @SerializedName("order_shipment_charges")
    @Expose
    private String order_shipment_charges;
    @SerializedName("order_service_fee")
    @Expose
    private String order_service_fee;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_price")
    @Expose
    private String orderPrice;
    @SerializedName("order_rating")
    @Expose
    private String orderRating;
    @SerializedName("order_rating_comment")
    @Expose
    private String orderRatingComment;
    @SerializedName("order_created_at")
    @Expose
    private String orderCreatedAt;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_phone")
    @Expose
    private String shopPhone;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(String orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    public String getOrderShopId() {
        return orderShopId;
    }

    public void setOrderShopId(String orderShopId) {
        this.orderShopId = orderShopId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderRating() {
        return orderRating;
    }

    public void setOrderRating(String orderRating) {
        this.orderRating = orderRating;
    }

    public String getOrderRatingComment() {
        return orderRatingComment;
    }

    public void setOrderRatingComment(String orderRatingComment) {
        this.orderRatingComment = orderRatingComment;
    }

    public String getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(String orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getOrder_service_fee() {
        return order_service_fee;
    }

    public void setOrder_service_fee(String order_service_fee) {
        this.order_service_fee = order_service_fee;
    }

    public String getOrder_rejection() {
        return order_rejection;
    }

    public void setOrder_rejection(String order_rejection) {
        this.order_rejection = order_rejection;
    }

    public String getOrder_shipment_charges() {
        return order_shipment_charges;
    }

    public void setOrder_shipment_charges(String order_shipment_charges) {
        this.order_shipment_charges = order_shipment_charges;
    }

    public String getOrder_tracking_id() {
        return order_tracking_id;
    }

    public void setOrder_tracking_id(String order_tracking_id) {
        this.order_tracking_id = order_tracking_id;
    }

    public String getOrder_label_id() {
        return order_label_id;
    }

    public void setOrder_label_id(String order_label_id) {
        this.order_label_id = order_label_id;
    }

    public String getOrder_tax() {
        return order_tax;
    }

    public void setOrder_tax(String order_tax) {
        this.order_tax = order_tax;
    }
}
