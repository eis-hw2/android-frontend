package com.pipipan.demo.ui.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.hjq.bar.TitleBar;
        import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.adapter.OrderGoodAdapter;
import com.pipipan.demo.widget.MarqueTextView;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import java.nio.file.attribute.AclEntryPermission;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.baidu.mapapi.BMapManager.getContext;

public class OrderUserDetailActivity extends AppCompatActivity implements XCollapsingToolbarLayout.OnScrimsListener {
    private static final String TAG = "OrderUserDetailActivity";

    TitleBar titleBar;
    Toolbar toolbar;
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    MarqueTextView address;
    Button contactParoxy;
    Button changeOrder;
    RecyclerView goods;
    TextView title;
    MapView mapView;

    Order order;
    BaiduMap baiduMap;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_order_user_detail);
        titleBar = (TitleBar) findViewById(R.id.titlebar);
        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        mCollapsingToolbarLayout = (XCollapsingToolbarLayout) findViewById(R.id.ctl_bar);
        address = (MarqueTextView) findViewById(R.id.address);
        contactParoxy = (Button) findViewById(R.id.contactProxy);
        changeOrder = (Button) findViewById(R.id.changeOrder);
        goods = (RecyclerView) findViewById(R.id.goods);
        title = (TextView) findViewById(R.id.title);
        mapView = (MapView) findViewById(R.id.map);
        initView();
        initData();
    }

    protected void initView() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.douban_blue_80_percent));
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        address.setSelected(true);
        baiduMap = mapView.getMap();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(16.5f));
        //TODO 改成Order的收货人address
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(Constants.address.getLatitude(), Constants.address.getLongitude())));
        //TODO 加入骑手和商家还有收货地址的点位
        addOverlay(R.mipmap.store, Constants.address.getLatitude(), Constants.address.getLongitude());
    }

    private void addOverlay(int id, Double latitude, Double longitude){
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(id);
        baiduMap.addOverlay(new MarkerOptions().position(new LatLng(Constants.address.getLatitude(), Constants.address.getLongitude())).icon(bitmapDescriptor));
    }

    protected void initData() {
        order = CommonUtil.gson.fromJson(getIntent().getStringExtra("order"), Order.class);
        if (order.getStatus().equals(Order.Status.WAITING)) changeOrder.setVisibility(View.VISIBLE);
        else contactParoxy.setVisibility(View.VISIBLE);
        Log.e(TAG, "initData: " + CommonUtil.gson.toJson(order));
        goods.setAdapter(new OrderGoodAdapter(getContext(), prepareGood()));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Thread.sleep(5000);
                    }catch (Exception e){
                        return;
                    }
                    //TODO restful调用获取订单信息
                    Log.e(TAG, "run: " + CommonUtil.gson.toJson(order));
                    runOnUiThread(()->{
                        baiduMap.clear();
                        //TODO 加入位置
                    });
                }
            }
        });
        thread.start();
    }

    private List<Good> prepareGood() {
        //TODO 从order中拿取数据
        List<Good> res = new ArrayList<>();
        for (int i=0; i<15; ++i){
            res.add(new Good());
        }
        return res;
    }

    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            title.setText("订单详情");
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }else {
            title.setText("");
            getWindow().setStatusBarColor(getResources().getColor(R.color.douban_blue_80_percent));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        thread.interrupt();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
