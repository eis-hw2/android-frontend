package com.pipipan.demo.ui.fragment;

import android.util.Log;
import android.view.View;

import com.pipipan.demo.common.Constants;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.network.Network;
import com.pipipan.demo.ui.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalOrderFragment extends FragmentOrder {
    private static final String TAG = "LocalOrderFragment";

    @Override
    public void initProxyOrderView() {
        super.initProxyOrderView();
        titleBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshData() {
        Network.getInstance().getOrder("NEARBY", Constants.address.getLatitude(), Constants.address.getLongitude(), null).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> ordersBody = response.body();
                orderAdapter = new OrderAdapter(getContext(), ordersBody);
                orders.setAdapter(orderAdapter);
                Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(ordersBody));
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }


}
