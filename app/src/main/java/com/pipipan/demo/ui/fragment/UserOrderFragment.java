package com.pipipan.demo.ui.fragment;

import com.pipipan.demo.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class UserOrderFragment extends FragmentOrder{
    @Override
    public List<Order> initOrderData() {
        //TODO 当前用户的订单
        List<Order> res = new ArrayList<>();
        for (int i=0; i<10; ++i) res.add(new Order());
        return res;
    }
}
