package com.pipipan.demo.ui.fragment;

import android.view.View;

import com.pipipan.demo.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class UncompleteOrderFragment extends FragmentOrder {
    @Override
    public void initProxyOrderView() {
        super.initProxyOrderView();
        titleBar.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(v->{

        });
    }

    @Override
    public List<Order> initOrderData() {
        //TODO 当前用户准备代跑腿的订单
        List<Order> res = new ArrayList<>();
        for (int i=0; i<3; ++i) {
            Order order = new Order();
            order.setStatus(Order.Status.BUYING);
            res.add(order);
        }
        return res;
    }
}
