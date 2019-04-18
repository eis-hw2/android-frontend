package com.pipipan.demo.domain;

public class Address {
    String addressLocationName;
    Double longitude;
    Double latitude;

    public Address(String addressLocationName, Double longitude, Double latitude) {
        this.addressLocationName = addressLocationName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getAddressLocationName() {
        return addressLocationName;
    }

    public void setAddressLocationName(String addressLocationName) {
        this.addressLocationName = addressLocationName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
