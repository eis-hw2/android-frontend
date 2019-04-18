package com.pipipan.demo.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.helper.GlideImageLoader;
import com.pipipan.demo.ui.activity.MapActivity;
import com.pipipan.demo.ui.activity.SelectAddressByMapActivity;
import com.pipipan.demo.ui.adapter.StoreAdapter;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;
import com.pipipan.image.ImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.constraint.Constraints.TAG;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目炫酷效果示例
 */
public class FragmentMain extends MyLazyFragment
        implements XCollapsingToolbarLayout.OnScrimsListener {
    List<String> images = new ArrayList<>();
    private static final String TAG = "FragmentMain";
    @BindView(R.id.abl_test_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.t_test_title)
    Toolbar mToolbar;
    @BindView(R.id.tb_test_a_bar)
    TitleBar mTitleBar;
    @BindView(R.id.address_image)
    ImageView addressImage;
    @BindView(R.id.tv_test_address)
    TextView mAddressView;
    @BindView(R.id.convenientBanner)
    Banner banner;
    @BindView(R.id.stores)
    RecyclerView recyclerView;

    StoreAdapter storeAdapter;
    Address address;
    LocationClient mLocationClient;
    Gson gson = new Gson();

    public static FragmentMain newInstance() {
        return new FragmentMain();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {
        // 给这个ToolBar设置顶部内边距，才能和TitleBar进行对齐
        ImmersionBar.setTitleBar(getFragmentActivity(), mToolbar);

        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        mAddressView.setOnClickListener((v -> {
            Intent intent = new Intent(getContext(), SelectAddressByMapActivity.class);
            startActivityForResult(intent, 1);
        }));
    }

    @Override
    protected void initData() {
        loadTestDatas();
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        storeAdapter = new StoreAdapter(getContext(), getStoreList());
        recyclerView.setAdapter(storeAdapter);
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }
        else requestLocation();
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        Log.e(TAG, "onReceiveLocation: initLocation");
        LocationClientOption clientOption = new LocationClientOption();
        clientOption.setIsNeedAddress(true);
        clientOption.setIsNeedAltitude(true);
        mLocationClient.setLocOption(clientOption);
    }

    private List<Store> getStoreList() {
        List<Store> stores = new ArrayList<>();
        for (int i=0; i<30; ++i)
            stores.add(new Store());
        return stores;

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }

    /**
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            mAddressView.setTextColor(getResources().getColor(R.color.black));
            addressImage.setImageResource(R.mipmap.address);
            getStatusBarConfig().statusBarDarkFont(true).init();
        }else {
            mAddressView.setTextColor(getResources().getColor(R.color.white));
            addressImage.setImageResource(R.mipmap.address_white);
            getStatusBarConfig().statusBarDarkFont(false).init();
        }
    }

    private void loadTestDatas() {
        //图片可能过期哦，自己换来测试吧
        images.add("http://image.tupian114.com/20181120/12097358.jpg");
        images.add("https://png.pngtree.com/png-clipart/20190116/ourlarge/pngtree-food-delicious-food-and-drink-a-bowl-of-rice-png-image_395602.jpg");
        images.add("http://bpic.588ku.com/element_origin_min_pic/19/03/07/26beb9fd1569c15e3da4f5b05bc5b77f.jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                address = gson.fromJson(data.getStringExtra("address"), Address.class);
                mAddressView.setText(address.getAddressLocationName());
        }
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            getActivity().runOnUiThread(() -> {
                address = new Address(getAddressLocationName(bdLocation), bdLocation.getLongitude(), bdLocation.getLatitude());
                Log.e(TAG, "onReceiveLocation: receiveLocation: " + gson.toJson(bdLocation));
                mAddressView.setText(address.getAddressLocationName());
                //TODO 初始化周围商店
            });
        }
    }

    @NonNull
    private String getAddressLocationName(BDLocation bdLocation) {
        return bdLocation.getDistrict() + " " + bdLocation.getStreet() + " " + bdLocation.getStreetNumber();
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
}