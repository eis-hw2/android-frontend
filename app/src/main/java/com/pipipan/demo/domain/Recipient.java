package com.pipipan.demo.domain;

public class Recipient {
    long id;
    Address address;
    String detaillocation;
    String contact;
    String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDetaillocation() {
        return detaillocation;
    }

    public void setDetaillocation(String detailLocation) {
        this.detaillocation = detailLocation;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String recipient) {
        this.contact = recipient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
