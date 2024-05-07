package com.softarena.checagocoffee.Acitivity.Products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softarena.checagocoffee.Acitivity.Shops.AllShop;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantItemModel;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantsModel;
import com.softarena.checagocoffee.Acitivity.Sizes.ProductSize;

import java.io.Serializable;
import java.util.List;

public class AllProduct implements Serializable {
    @SerializedName("product_id")
    @Expose
    private String productId;

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("totalRecords")
    @Expose
    private int totalRecords;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("product_shop_id")
    @Expose
    private String productShopId;
    @SerializedName("product_sub_cat_id")
    @Expose
    private String productSubCatId;

    @SerializedName("product_service_fee")
    @Expose
    private String product_service_fee;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;
    @SerializedName("product_weight")
    @Expose
    private String product_weight;
    @SerializedName("product_weight_unit")
    @Expose
    private String product_weight_unit;
    @SerializedName("product_image")
    @Expose
    private String productImage;

    @SerializedName("product_cat_id")
    @Expose
    private int product_cat_id;

    @SerializedName("product_cat_name")
    @Expose
    private String product_cat_name;
    @SerializedName("product_status")
    @Expose
    private String productStatus;
    @SerializedName("product_created_at")
    @Expose
    private String productCreatedAt;
    @SerializedName("product_sizes")
    @Expose
    private List<ProductSize> productSizes = null;

    @SerializedName("product_ingredients")
    @Expose
    private List<IngrediantsModel> productIngredients = null;
    @SerializedName("ingredient_all")
    @Expose
    private List<IngrediantItemModel> ingredientAll = null;
    @SerializedName("shop_details")
    @Expose
    private List<AllShop> shop_details = null;
    AllShop allShop;
    private boolean expanded;
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductShopId() {
        return productShopId;
    }

    public void setProductShopId(String productShopId) {
        this.productShopId = productShopId;
    }

    public String getProductSubCatId() {
        return productSubCatId;
    }

    public void setProductSubCatId(String productSubCatId) {
        this.productSubCatId = productSubCatId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductCreatedAt() {
        return productCreatedAt;
    }

    public void setProductCreatedAt(String productCreatedAt) {
        this.productCreatedAt = productCreatedAt;
    }

    public List<ProductSize> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }

    public List<IngrediantsModel> getProductIngredients() {
        return productIngredients;
    }

    public void setProductIngredients(List<IngrediantsModel> productIngredients) {
        this.productIngredients = productIngredients;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
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

    public AllShop getAllShop() {
        return allShop;
    }

    public void setAllShop(AllShop allShop) {
        this.allShop = allShop;
    }

    public List<IngrediantItemModel> getIngredientAll() {
        return ingredientAll;
    }

    public void setIngredientAll(List<IngrediantItemModel> ingredientAll) {
        this.ingredientAll = ingredientAll;
    }

    public List<AllShop> getShop_details() {
        return shop_details;
    }

    public void setShop_details(List<AllShop> shop_details) {
        this.shop_details = shop_details;
    }

    public int getProduct_cat_id() {
        return product_cat_id;
    }

    public void setProduct_cat_id(int product_cat_id) {
        this.product_cat_id = product_cat_id;
    }

    public String getProduct_cat_name() {
        return product_cat_name;
    }

    public void setProduct_cat_name(String product_cat_name) {
        this.product_cat_name = product_cat_name;
    }

    public String getProduct_service_fee() {
        return product_service_fee;
    }

    public void setProduct_service_fee(String product_service_fee) {
        this.product_service_fee = product_service_fee;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getProduct_weight_unit() {
        return product_weight_unit;
    }

    public void setProduct_weight_unit(String product_weight_unit) {
        this.product_weight_unit = product_weight_unit;
    }
}
