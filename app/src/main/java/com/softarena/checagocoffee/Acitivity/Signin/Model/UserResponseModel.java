package com.softarena.checagocoffee.Acitivity.Signin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private Integer uSERID;
    @SerializedName("user_name")
    @Expose
    private String uSERNAME;
    @SerializedName("user_email")
    @Expose
    private String uSEREMAIL;
    @SerializedName("user_zip_code")
    @Expose
    private String user_zip_code;

    @SerializedName("user_state")
    @Expose
    private String user_state;

    @SerializedName("user_city")
    @Expose
    private String user_city;
    @SerializedName("user_image")
    @Expose
    private String uSERIMAGE;
    @SerializedName("shop_id")
    @Expose
    private String SHOP_ID;
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("user_type")
    @Expose
    private String uSERTYPE;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("user_status")
    @Expose
    private String uSERSTATUS;

    @SerializedName("user_address")
    @Expose
    private String USER_ADDRESS;
    @SerializedName("user_lat")
    @Expose
    private String USER_LAT;
    @SerializedName("user_lng")
    @Expose
    private String USER_LNG;
    @SerializedName("JWT")
    @Expose
    private String jWT;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("status_description")
    @Expose
    private String status_description;
    @SerializedName("carrier_status_description")
    @Expose
    private String carrier_status_description;
    @SerializedName("estimated_delivery_date")
    @Expose
    private String estimated_delivery_date;
    @SerializedName("actual_delivery_date")
    @Expose
    private String actual_delivery_date;
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getuSERID() {
        return uSERID;
    }

    public void setuSERID(Integer uSERID) {
        this.uSERID = uSERID;
    }

    public String getuSERNAME() {
        return uSERNAME;
    }

    public void setuSERNAME(String uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public String getuSEREMAIL() {
        return uSEREMAIL;
    }

    public void setuSEREMAIL(String uSEREMAIL) {
        this.uSEREMAIL = uSEREMAIL;
    }

    public String getuSERIMAGE() {
        return uSERIMAGE;
    }

    public void setuSERIMAGE(String uSERIMAGE) {
        this.uSERIMAGE = uSERIMAGE;
    }

    public String getuSERTYPE() {
        return uSERTYPE;
    }

    public void setuSERTYPE(String uSERTYPE) {
        this.uSERTYPE = uSERTYPE;
    }

    public String getuSERSTATUS() {
        return uSERSTATUS;
    }

    public void setuSERSTATUS(String uSERSTATUS) {
        this.uSERSTATUS = uSERSTATUS;
    }

    public String getjWT() {
        return jWT;
    }

    public void setjWT(String jWT) {
        this.jWT = jWT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSHOP_ID() {
        return SHOP_ID;
    }

    public void setSHOP_ID(String SHOP_ID) {
        this.SHOP_ID = SHOP_ID;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUSER_ADDRESS() {
        return USER_ADDRESS;
    }

    public void setUSER_ADDRESS(String USER_ADDRESS) {
        this.USER_ADDRESS = USER_ADDRESS;
    }

    public String getUSER_LAT() {
        return USER_LAT;
    }

    public void setUSER_LAT(String USER_LAT) {
        this.USER_LAT = USER_LAT;
    }

    public String getUSER_LNG() {
        return USER_LNG;
    }

    public void setUSER_LNG(String USER_LNG) {
        this.USER_LNG = USER_LNG;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUser_zip_code() {
        return user_zip_code;
    }

    public void setUser_zip_code(String user_zip_code) {
        this.user_zip_code = user_zip_code;
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    public String getCarrier_status_description() {
        return carrier_status_description;
    }

    public void setCarrier_status_description(String carrier_status_description) {
        this.carrier_status_description = carrier_status_description;
    }

    public String getEstimated_delivery_date() {
        return estimated_delivery_date;
    }

    public void setEstimated_delivery_date(String estimated_delivery_date) {
        this.estimated_delivery_date = estimated_delivery_date;
    }

    public String getActual_delivery_date() {
        return actual_delivery_date;
    }

    public void setActual_delivery_date(String actual_delivery_date) {
        this.actual_delivery_date = actual_delivery_date;
    }
}