package com.softarena.checagocoffee.Acitivity.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Shops.AllShop;

import java.io.Serializable;
import java.util.List;

public class AllCategory implements Serializable {
    @SerializedName("category_id")
    @Expose
    private int categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("all_shops")
    @Expose
    private List<AllShop> allShops = null;

    @SerializedName("all_products")
    @Expose
    private List<AllProduct> allProducts = null;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<AllShop> getAllShops() {
        return allShops;
    }

    public void setAllShops(List<AllShop> allShops) {
        this.allShops = allShops;
    }

    public List<AllProduct> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<AllProduct> allProducts) {
        this.allProducts = allProducts;
    }
}
