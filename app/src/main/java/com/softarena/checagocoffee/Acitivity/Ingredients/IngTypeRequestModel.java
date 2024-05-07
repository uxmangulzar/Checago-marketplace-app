package com.softarena.checagocoffee.Acitivity.Ingredients;

import java.util.List;

public class IngTypeRequestModel {
    String ing_type_id;
    List<ProductIngRequestModel> product_ingredients;

    public String getIng_type_id() {
        return ing_type_id;
    }

    public void setIng_type_id(String ing_type_id) {
        this.ing_type_id = ing_type_id;
    }

    public List<ProductIngRequestModel> getProduct_ingredients() {
        return product_ingredients;
    }

    public void setProduct_ingredients(List<ProductIngRequestModel> product_ingredients) {
        this.product_ingredients = product_ingredients;
    }
}
