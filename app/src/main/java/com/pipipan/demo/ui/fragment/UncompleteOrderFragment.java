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

import java.util.ArrayList;
import java.util.List;

public class UncompleteOrderFragment extends FragmentOrder {
    private static final String TAG = "UncompleteOrderFragment";
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
            //TODO 上传信息到对应order
            List<Order> orders = orderAdapter.getData();
            for (Order order : orders){
                Log.e(TAG, "onReceiveLocation: "+ CommonUtil.gson.toJson(order));
            }
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
