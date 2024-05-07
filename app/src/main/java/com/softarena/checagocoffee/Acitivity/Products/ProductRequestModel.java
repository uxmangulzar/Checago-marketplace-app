package com.softarena.checagocoffee.Acitivity.Products;


import com.softarena.checagocoffee.Acitivity.Ingredients.IngTypeRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.FilesModel;

import java.util.List;

public class ProductRequestModel {
    String user_id,shop_id,sub_cat_id,limit,page,name,description,quantity,product_id,u_type,cat_id;
    List<FilesModel> files;
    List<IngTypeRequestModel> product_ing_types;
    String product_qty,product_size_id;

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

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public List<FilesModel> getFiles() {
        return files;
    }

    public void setFiles(List<FilesModel> files) {
        this.files = files;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public List<IngTypeRequestModel> getProduct_ing_types() {
        return product_ing_types;
    }

    public void setProduct_ing_types(List<IngTypeRequestModel> product_ing_types) {
        this.product_ing_types = product_ing_types;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_size_id() {
        return product_size_id;
    }

    public void setProduct_size_id(String product_size_id) {
        this.product_size_id = product_size_id;
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
}
