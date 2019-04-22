package com.pipipan.demo.domain;

import com.pipipan.demo.common.Constants;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public static Order createOrder(Store store) {
        Order order = new Order();
        if (!Constants.user.getAddress().isEmpty())
            order.setRecipient(Constants.user.getAddress().get(0));
        else order.setRecipient(new NullRecipient());
        order.setProxyprice(10);
        order.setBuyer(Constants.user);
        order.setStore(store);
        order.setGoods(new ArrayList<>());
        order.setStatus(Status.WAITING);
        return order;
    }

    public enum Status {
        WAITING, BUYING, SENDING, TO_BE_CHECKED, EXPIRED, COMPLETED;
    }
    private long id;
    private User buyer;
    private User proxy;
    private Recipient recipient;
    private List<Good> goods;
    private double goodsprice;
    private double proxyprice;
    private String modifiedtime;
    private Order.Status status;
    private Store store;
    private Address proxyLocation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getProxy() {
        return proxy;
    }

    public void setProxy(User proxy) {
        this.proxy = proxy;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public double getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(double goodsprice) {
        this.goodsprice = goodsprice;
    }

    public double getProxyprice() {
        return proxyprice;
    }

    public void setProxyprice(double proxyprice) {
        this.proxyprice = proxyprice;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Address getProxyLocation() {
        return proxyLocation;
    }

    public void setProxyLocation(Address proxyLocation) {
        this.proxyLocation = proxyLocation;
    }
}
