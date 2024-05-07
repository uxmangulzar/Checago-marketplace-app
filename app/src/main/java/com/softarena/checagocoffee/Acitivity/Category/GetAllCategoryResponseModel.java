package com.softarena.checagocoffee.Acitivity.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllCategoryResponseModel {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("totalRecords")
    @Expose
    private int totalRecords;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("all_categories")
    @Expose
    private List<AllCategoryResponseModel> allCategoryResponseModelList = null;
    private final static long serialVersionUID = 6236296414307954033L;

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

    public List<AllCategoryResponseModel> getAllCategoryResponseModelList() {
        return allCategoryResponseModelList;
    }

    public void setAllCategories(List<AllCategoryResponseModel> allCategories) {
        this.allCategoryResponseModelList = allCategories;
    }

}
