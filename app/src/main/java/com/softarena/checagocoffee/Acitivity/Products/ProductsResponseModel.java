package com.softarena.checagocoffee.Acitivity.Products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResponseModel {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("totalRecords")
    @Expose
    private int totalRecords;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("all_products")
    @Expose
    private List<AllProduct> allProducts = null;
    @SerializedName("order_products")
    @Expose
    private List<AllProduct> order_products = null;

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

    public List<AllProduct> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<AllProduct> allProducts) {
        this.allProducts = allProducts;
    }

    public List<AllProduct> getOrder_products() {
        return order_products;
    }

    public void setOrder_products(List<AllProduct> order_products) {
        this.order_products = order_products;
    }
}
