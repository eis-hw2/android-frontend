package com.pipipan.demo.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MapActivity extends MyActivity {
    private static final String TAG = "MapActivity";
    LocationClient mLocationClient;
    @BindView(R.id.position_text_view)
    TextView position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this, permissions, 1);
        }
        else requestLocation();
    }

    private void requestLocation() {
        initLocation();
        Log.e(TAG, "onReceiveLocation: startLocation");
        mLocationClient.start();
    }

    private void initLocation() {
        Log.e(TAG, "onReceiveLocation: initLocation");
        LocationClientOption clientOption = new LocationClientOption();
        clientOption.setScanSpan(5000);
        mLocationClient.setLocOption(clientOption);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            toast("必须同意所有权限才能使用本系统");
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    toast("发生未知错误");
                    finish();
                }
                break;
        }
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            runOnUiThread(() -> {
                Log.e(TAG, "onReceiveLocation: receiveLocation");
                StringBuilder sb = new StringBuilder();
                sb.append("纬度:").append(bdLocation.getLatitude()).append('\n');
                sb.append("经度:").append(bdLocation.getLongitude()).append('\n');
                sb.append("定位方式:");
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) sb.append("GPS");
                else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) sb.append("网络");
                else {
                    sb.append(bdLocation.getLocType()+" "+bdLocation.getLocTypeDescription());
                };
                position.setText(sb.toString());
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
