package com.pipipan.demo.ui.fragment;

import android.nfc.tech.TagTechnology;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.network.Network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UncompleteOrderFragment extends FragmentOrder {
    private static final String TAG = "UncompleteOrderFragment";
    static List<Order> orders;
    LocationClient mLocationClient;
    @Override
    public void initProxyOrderView() {
        super.initProxyOrderView();
        titleBar.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(v->{

        });
    }

    @Override
    protected void initData() {
        super.initData();
        mLocationClient = new LocationClient(getContext().getApplicationContext());
        initLocation();
        mLocationClient.registerLocationListener(new MyLocationListener());
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            double latitude = bdLocation.getLatitude();
            double longtitude = bdLocation.getLongitude();
            List<Order> orders = orderAdapter.getData();
            if (orders == null) return;
            for (Order order : orders){
                Log.e(TAG, "onReceiveLocation: "+ CommonUtil.gson.toJson(order));
                order.getAddress().setLatitude(latitude);
                order.getAddress().setLatitude(longtitude);
                Network.getInstance().modifyOrderById(order.getId(), order).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {

                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    public List<Order> initOrderData() {
        //TODO 当前用户准备代跑腿的订单
        Network.getInstance().getOrder("PROXY", null, null, Constants.user.getId()).enqueue(new Callback<List<Order>>() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
