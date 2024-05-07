package com.softarena.checagocoffee.Acitivity.Orders;

import com.softarena.checagocoffee.Acitivity.Products.ProductRequestModel;

import java.util.List;

public class OrderRequestModel {
    String user_id,shop_id,order_time,limit,page,tax,service_fee,stripe_card_id,order_id,rating,comment,u_type,cat_id,order_date,amount,weight_unit,weight,shipment_fee;

    List<ProductRequestModel> products;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public List<ProductRequestModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequestModel> products) {
        this.products = products;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShipment_fee() {
        return shipment_fee;
    }

    public void setShipment_fee(String shipment_fee) {
        this.shipment_fee = shipment_fee;
    }

    public String getStripe_card_id() {
        return stripe_card_id;
    }

    public void setStripe_card_id(String stripe_card_id) {
        this.stripe_card_id = stripe_card_id;
    }

    public String getService_fee() {
        return service_fee;
    }

    public void setService_fee(String service_fee) {
        this.service_fee = service_fee;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
