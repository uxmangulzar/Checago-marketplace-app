package com.softarena.checagocoffee.Acitivity.Signin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;

import java.util.List;

public class UsermainResponseModel {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("customer_details")
    @Expose
    private List<UserResponseModel> customer_details;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<UserResponseModel> getCustomer_details() {
        return customer_details;
    }

    public void setCustomer_details(List<UserResponseModel> customer_details) {
        this.customer_details = customer_details;
    }

}
