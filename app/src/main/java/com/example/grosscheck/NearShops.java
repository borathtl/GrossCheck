package com.example.grosscheck;

import androidx.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
public class NearShops {
    @SerializedName("id")
    @Nullable
    private String id;
    @SerializedName("name")
    @Nullable
    private String name;
    @SerializedName("city")
    @Nullable
    private String city;

    public NearShops(@Nullable String name, Double lat, Double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    @SerializedName("county")
    @Nullable
    private String county;
    @SerializedName("province")
    @Nullable
    private String province;
    @SerializedName("address")
    @Nullable
    private String address;
    @SerializedName("lat")
    private Double lat = 0.0D;
    @SerializedName("long")
    private Double lng = 0.0D;

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }

    @Nullable
    public String getCounty() {
        return county;
    }

    public void setCounty(@Nullable String county) {
        this.county = county;
    }

    @Nullable
    public String getProvince() {
        return province;
    }

    public void setProvince(@Nullable String province) {
        this.province = province;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(@Nullable Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(@Nullable Double lng) {
        this.lng = lng;
    }
}
