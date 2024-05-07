package com.softarena.checagocoffee.Acitivity.Ingredients;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class IngrediantsModel implements Serializable {
    @SerializedName("ingredient_type_id")
    @Expose
    private String ingredientTypeId;
    @SerializedName("ingredient_type_name")
    @Expose
    private String ingredientTypeName;
    @SerializedName("ingredient_all")
    @Expose
    private List<IngrediantItemModel> ingredientAll = null;
    @SerializedName("product_ingredients")
    @Expose
    private List<IngrediantItemModel> product_ingredients = null;
    IngrediantItemModel ingrediantItemModel;
    public IngrediantsModel() {
    }

    public IngrediantsModel(String ingredientTypeId, List<IngrediantItemModel> ingredientAll) {
        this.ingredientTypeId = ingredientTypeId;
        this.ingredientAll = ingredientAll;
    }

    public String getIngredientTypeId() {
        return ingredientTypeId;
    }

    public void setIngredientTypeId(String ingredientTypeId) {
        this.ingredientTypeId = ingredientTypeId;
    }

    public String getIngredientTypeName() {
        return ingredientTypeName;
    }

    public void setIngredientTypeName(String ingredientTypeName) {
        this.ingredientTypeName = ingredientTypeName;
    }

    public List<IngrediantItemModel> getIngredientAll() {
        return ingredientAll;
    }

    public void setIngredientAll(List<IngrediantItemModel> ingredientAll) {
        this.ingredientAll = ingredientAll;
    }

    public List<IngrediantItemModel> getProduct_ingredients() {
        return product_ingredients;
    }

    public void setProduct_ingredients(List<IngrediantItemModel> product_ingredients) {
        this.product_ingredients = product_ingredients;
    }

    public IngrediantItemModel getIngrediantItemModel() {
        return ingrediantItemModel;
    }

    public void setIngrediantItemModel(IngrediantItemModel ingrediantItemModel) {
        this.ingrediantItemModel = ingrediantItemModel;
    }
}
