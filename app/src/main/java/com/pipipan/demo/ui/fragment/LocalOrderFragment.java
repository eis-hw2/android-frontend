package com.pipipan.demo.ui.fragment;

import android.util.Log;
import android.view.View;

import com.pipipan.demo.common.Constants;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.network.Network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalOrderFragment extends FragmentOrder {
    private static final String TAG = "LocalOrderFragment";
    static List<Order> orders;
    @Override
    public void initProxyOrderView() {
        super.initProxyOrderView();
        titleBar.setVisibility(View.GONE);
    }

    @Override
    public List<Order> initOrderData() {
        //TODO 当前用户附近的订单
        Network.getInstance().getOrder("NEARBY", Constants.address.getLatitude(), Constants.address.getLongitude(), null).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orders = response.body();
                Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(orders));
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
        return orders;
    }
}
