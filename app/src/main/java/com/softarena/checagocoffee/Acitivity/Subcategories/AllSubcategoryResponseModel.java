package com.softarena.checagocoffee.Acitivity.Subcategories;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllSubcategoryResponseModel implements Serializable
{

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("subcategory_name")
    @Expose
    private String subcategoryName;
    @SerializedName("subcategory_image")
    @Expose
    private String subcategoryImage;
    @SerializedName("subcategory_description")
    @Expose
    private String subcategoryDescription;
    @SerializedName("subcategory_status")
    @Expose
    private String subcategoryStatus;
    private final static long serialVersionUID = -4981217525536077923L;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getSubcategoryImage() {
        return subcategoryImage;
    }

    public void setSubcategoryImage(String subcategoryImage) {
        this.subcategoryImage = subcategoryImage;
    }

    public String getSubcategoryDescription() {
        return subcategoryDescription;
    }

    public void setSubcategoryDescription(String subcategoryDescription) {
        this.subcategoryDescription = subcategoryDescription;
    }

    public String getSubcategoryStatus() {
        return subcategoryStatus;
    }

    public void setSubcategoryStatus(String subcategoryStatus) {
        this.subcategoryStatus = subcategoryStatus;
    }

}