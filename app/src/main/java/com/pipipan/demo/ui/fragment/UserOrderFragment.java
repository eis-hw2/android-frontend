package com.pipipan.demo.ui.fragment;

import android.util.Log;

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

public class UserOrderFragment extends FragmentOrder{
    private static final String TAG = "UserOrderFragment";

    @Override
    public void refreshData() {
        Network.getInstance().getOrder("USER", null, null, Constants.user.getId()).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> currentorders = response.body();
                Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(currentorders));
                orderAdapter = new OrderAdapter(getContext(), currentorders);
                orders.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }
}
