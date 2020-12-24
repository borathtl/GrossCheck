package com.example.grosscheck;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productName")
    @Nullable
    private String productName;
    @SerializedName("productPrice")
    @Nullable
    private String productPrice;
    @SerializedName("productImage")
    @Nullable
    private String productImage;

    public Product(@Nullable String productName, @Nullable String productPrice, @Nullable String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    @Nullable
    public String getProductName() {
        return productName;
    }

    public void setProductName(@Nullable String productName) {
        this.productName = productName;
    }

    @Nullable
    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(@Nullable String productPrice) {
        this.productPrice = productPrice;
    }

    @Nullable
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(@Nullable String productImage) {
        this.productImage = productImage;
    }
}
