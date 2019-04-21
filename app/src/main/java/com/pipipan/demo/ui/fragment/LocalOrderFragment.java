package com.pipipan.demo.ui.fragment;

import android.view.View;

import com.pipipan.demo.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class LocalOrderFragment extends FragmentOrder {
    @Override
    public void initProxyOrderView() {
        super.initProxyOrderView();
        titleBar.setVisibility(View.GONE);
    }

    @Override
    public List<Order> initOrderData() {
        //TODO 当前用户附近的订单
        List<Order> res = new ArrayList<>();
        for (int i=0; i<5; ++i) res.add(new Order());
        return res;
    }
}
