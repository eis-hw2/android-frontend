package com.pipipan.demo.ui.fragment;

import android.util.Log;

import com.pipipan.demo.common.Constants;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.network.Network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderFragment extends FragmentOrder{

    private static final String TAG = "UserOrderFragment";
    static List<Order> orders;
    @Override
    public List<Order> initOrderData() {
        //TODO 当前用户的订单
        Network.getInstance().getOrder("USER", null, null, Constants.user.getId()).enqueue(new Callback<List<Order>>() {
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
