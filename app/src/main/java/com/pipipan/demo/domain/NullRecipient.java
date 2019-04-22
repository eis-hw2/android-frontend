package com.pipipan.demo.domain;

public class NullRecipient extends Recipient {
    @Override
    public String getDetaillocation() {
        return "不知道路";
    }

    @Override
    public Address getAddress() {
        return new Address("不知道区", 0.0, 0.0);
    }

    @Override
    public String getContact() {
        return "不知道先生";
    }

    @Override
    public String getPhone() {
        return "不知道电话";
    }
}
