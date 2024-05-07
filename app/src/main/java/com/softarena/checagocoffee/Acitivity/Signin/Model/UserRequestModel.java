package com.softarena.checagocoffee.Acitivity.Signin.Model;

public class UserRequestModel {
    String user_email,user_password,fcm_token,user_type,user_id,login_from,
            to_email,from_email,code,new_password,shop_id,from_app,weight_unit,weight,ship_address,tracking_number,u_type,ship_lat,ship_lng,ship_zip_code;

    public UserRequestModel(String user_email, String user_password, String fcm_token, String user_type) {
        this.user_email = user_email;
        this.user_password = user_password;
        this.fcm_token = fcm_token;
        this.user_type = user_type;
    }

    public UserRequestModel() {
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_from() {
        return login_from;
    }

    public void setLogin_from(String login_from) {
        this.login_from = login_from;
    }

    public String getTo_email() {
        return to_email;
    }

    public void setTo_email(String to_email) {
        this.to_email = to_email;
    }

    public String getFrom_email() {
        return from_email;
    }

    public void setFrom_email(String from_email) {
        this.from_email = from_email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getWeight_unit() {
        return weight_unit;
    }

    public void setWeight_unit(String weight_unit) {
        this.weight_unit = weight_unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShip_address() {
        return ship_address;
    }

    public void setShip_address(String ship_address) {
        this.ship_address = ship_address;
    }

    public String getShip_lat() {
        return ship_lat;
    }

    public void setShip_lat(String ship_lat) {
        this.ship_lat = ship_lat;
    }

    public String getShip_lng() {
        return ship_lng;
    }

    public void setShip_lng(String ship_lng) {
        this.ship_lng = ship_lng;
    }

    public String getShip_zip_code() {
        return ship_zip_code;
    }

    public void setShip_zip_code(String ship_zip_code) {
        this.ship_zip_code = ship_zip_code;
    }

    public String getFrom_app() {
        return from_app;
    }

    public void setFrom_app(String from_app) {
        this.from_app = from_app;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getU_type() {
        return u_type;
    }

    public void setU_type(String u_type) {
        this.u_type = u_type;
    }
}
