package com.pipipan.demo.domain;

import java.util.List;

public class User {
    Long id;
    String username;
    String phone;
    String password;
    String avatar;
    List<Recipient> recipientaddress;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Recipient> getRecipientaddress() {
        return recipientaddress;
    }

    public void setRecipientaddress(List<Recipient> address) {
        this.recipientaddress = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
