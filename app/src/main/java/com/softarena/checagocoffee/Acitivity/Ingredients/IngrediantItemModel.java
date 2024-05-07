package com.softarena.checagocoffee.Acitivity.Ingredients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IngrediantItemModel implements Serializable {
    @SerializedName("ingredient_id")
    @Expose
    private String ingredientId;
    @SerializedName("product_ingredient_type_id")
    @Expose
    private String productIngredientTypeId;
    @SerializedName("ingredient_name")
    @Expose
    private String ingredientName;
    @SerializedName("ingredient_price")
    @Expose
    private String ingredientPrice;
    @SerializedName("ingredient_created_at")
    @Expose
    private String ingredientCreatedAt;

    public IngrediantItemModel(String ingredientId, String ingredientName, String ingredientPrice) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientPrice = ingredientPrice;
    }

    public IngrediantItemModel() {
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getProductIngredientTypeId() {
        return productIngredientTypeId;
    }

    public void setProductIngredientTypeId(String productIngredientTypeId) {
        this.productIngredientTypeId = productIngredientTypeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(String ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public String getIngredientCreatedAt() {
        return ingredientCreatedAt;
    }

    public void setIngredientCreatedAt(String ingredientCreatedAt) {
        this.ingredientCreatedAt = ingredientCreatedAt;
    }
}
