package com.softarena.checagocoffee.Acitivity.Subcategories;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetAllSubCategoryResponseModel implements Serializable
{

    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("totalRecords")
    @Expose
    private int totalRecords;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("all_subcategories")
    @Expose
    private List<AllSubcategoryResponseModel> allSubcategoryResponseModelList = null;
    private final static long serialVersionUID = -7294146922630362336L;

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

    public List<AllSubcategoryResponseModel> getAllSubcategoryResponseModelList() {
        return allSubcategoryResponseModelList;
    }

    public void setAllSubcategories(List<AllSubcategoryResponseModel> allSubcategories) {
        this.allSubcategoryResponseModelList = allSubcategories;
    }

}