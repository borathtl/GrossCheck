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
    @SerializedName("barcode")
    private String barcode;

    public Product(@Nullable String productName, @Nullable String productPrice, @Nullable String productImage,@Nullable String barcode) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.barcode = barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
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
