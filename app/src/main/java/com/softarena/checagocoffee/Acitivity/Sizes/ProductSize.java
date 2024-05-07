package com.softarena.checagocoffee.Acitivity.Sizes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSize implements Serializable {
    @SerializedName("product_size_id")
    @Expose
    private String productSizeId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_size_name")
    @Expose
    private String productSizeName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    private boolean selected;

    public ProductSize() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ProductSize(String productSizeId, String productSizeName, String productPrice) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productPrice = productPrice;
    }

    public ProductSize(String productSizeName, String productPrice) {
        this.productSizeName = productSizeName;
        this.productPrice = productPrice;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSizeName() {
        return productSizeName;
    }

    public void setProductSizeName(String productSizeName) {
        this.productSizeName = productSizeName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
