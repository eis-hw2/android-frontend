package com.pipipan.demo.domain;

class NullRecipient extends Recipient {
    @Override
    public String getDetailLocation() {
        return "不知道路";
    }

    @Override
    public Address getAddress() {
        Address address = new Address("不知道区", 0.0, 0.0);
        return address;
    }

    @Override
    public String getRecipient() {
        return "不知道先生";
    }

    @Override
    public String getPhone() {
        return "不知道电话";
    }
}
