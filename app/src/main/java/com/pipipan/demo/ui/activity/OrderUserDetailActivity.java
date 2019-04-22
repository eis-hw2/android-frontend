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
import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.domain.Recipient;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.network.Network;
import com.pipipan.demo.ui.adapter.OrderGoodAdapter;
import com.pipipan.demo.widget.MarqueTextView;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.baidu.mapapi.BMapManager.getContext;

public class OrderUserDetailActivity extends AppCompatActivity implements XCollapsingToolbarLayout.OnScrimsListener {
    private static final String TAG = "OrderUserDetailActivity";

    TitleBar titleBar;
    Toolbar toolbar;
    MarqueTextView address;
    Button contactParoxy;
    Button changeOrder;
    RecyclerView goods;
    TextView title;
    MapView mapView;
    TextView totalPrice;
    TextView storeName;
    TextView proxyPrice;
    TextView recipient;

    Order order;
    BaiduMap baiduMap;
    Thread thread;
    Recipient userRecipient;
    Store store;
    Address proxyLocation;

    public void setOrder(Order order){
        this.order = order;
        proxyLocation = order.getAddress();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_order_user_detail);
        titleBar = (TitleBar) findViewById(R.id.titlebar);
        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        address = (MarqueTextView) findViewById(R.id.address);
        contactParoxy = (Button) findViewById(R.id.contactProxy);
        changeOrder = (Button) findViewById(R.id.changeOrder);
        goods = (RecyclerView) findViewById(R.id.goods);
        title = (TextView) findViewById(R.id.title);
        mapView = (MapView) findViewById(R.id.map);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        storeName = (TextView) findViewById(R.id.storeName);
        proxyPrice = (TextView) findViewById(R.id.proxyPrice);
        recipient = (TextView) findViewById(R.id.recipient);
        initView();
        initData();
    }

    public void cancel(){
        thread.interrupt();
    }

    protected void initView() {
        title.setText("订单详情");
        order = Constants.order;
        userRecipient = order.getRecipient();
        store = order.getStore();
        proxyLocation = order.getAddress();
        initRecipient();
        if (order.getStore() != null) storeName.setText(order.getStore().getStorename());
        totalPrice.setText(String.valueOf(order.getGoodsprice() + order.getProxyprice()));
        proxyPrice.setText(String.valueOf(order.getProxyprice()));
        getWindow().setStatusBarColor(getResources().getColor(R.color.douban_blue_80_percent));
        //设置渐变监听
        address.setSelected(true);
        baiduMap = mapView.getMap();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(16f));
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(userRecipient.getAddress().getLatitude(), userRecipient.getAddress().getLongitude())));
        addOverlays();
    }

    private void addOverlays() {
        addOverlay(R.mipmap.store, store.getAddress().getLatitude(), store.getAddress().getLongitude());
        addOverlay(R.mipmap.home_address, userRecipient.getAddress().getLatitude(), userRecipient.getAddress().getLongitude());
        if (proxyLocation != null && order.getStatus().equals(Order.Status.BUYING)) {
            addOverlay(R.mipmap.proxy, proxyLocation.getLatitude(), proxyLocation.getLongitude());
        }
    }

    private void initRecipient() {
        address.setText(userRecipient.getAddress().getAddress() + " " + userRecipient.getDetaillocation());
        recipient.setText(userRecipient.getContact() + " " + userRecipient.getPhone());
    }

    private void addOverlay(int id, Double latitude, Double longitude){
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(id);
        baiduMap.addOverlay(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(bitmapDescriptor));
    }

    protected void initData() {
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
                    Network.getInstance().getOrderById(order.getId()).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            Order order = response.body();
                            Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(order) );
                            setOrder(order);
                            if (order.getStatus().equals(Order.Status.COMPLETED)) cancel();
                            else {
                                runOnUiThread(()->{
                                    baiduMap.clear();
                                    addOverlays();
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {

                        }
                    });
                    Log.e(TAG, "run: " + CommonUtil.gson.toJson(order));
                }
            }
        });
        if (order.getStatus().equals(Order.Status.BUYING)) {
            thread.start();
        }
    }

    private List<Good> prepareGood() {
        return order.getGoods();
    }

    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
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
        if (order.getStatus().equals(Order.Status.BUYING)) {
            thread.interrupt();
        }
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
