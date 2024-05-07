package com.softarena.checagocoffee.Acitivity.Home;


public class SlidesModel {
    String id;
    String image;
    String description;
    String adlink;
    public SlidesModel(String id, String image, String description,String adlink) {
        this.id = id;
        this.adlink = adlink;
        this.image = image;
        this.description = description;
    }

    public String getAdlink() {
        return adlink;
    }

    public void setAdlink(String adlink) {
        this.adlink = adlink;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
