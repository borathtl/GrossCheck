package com.example.grosscheck;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import kotlin.jvm.Transient;

public class Category {
    @SerializedName("categoryName")
    @Nullable
    private String categoryName;
    @SerializedName("subcategories")
    @Nullable
    private ArrayList<Category> subcategories;
    @SerializedName("productList")
    @Nullable
    private ArrayList<Product> productList;
    @Transient
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Nullable
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(@Nullable ArrayList<Product> productList) {
        this.productList = productList;
    }

    public Category(@Nullable String categoryName) {
        this.categoryName = categoryName;
    }

    public Category() {
    }

    public static Category fromJson(String category) {
        Gson gson = new Gson();
        return gson.fromJson(category, Category.class);
    }

    @Nullable
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@Nullable String categoryName) {
        this.categoryName = categoryName;
    }

    @Nullable
    public ArrayList<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(@Nullable ArrayList<Category> subcategories) {
        this.subcategories = subcategories;
    }
}
