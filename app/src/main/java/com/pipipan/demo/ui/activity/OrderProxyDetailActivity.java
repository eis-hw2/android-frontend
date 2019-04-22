package com.pipipan.demo.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.domain.Recipient;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.network.Network;
import com.pipipan.demo.ui.adapter.OrderGoodAdapter;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProxyDetailActivity extends MyActivity implements XCollapsingToolbarLayout.OnScrimsListener {
    private static final String TAG = "OrderProxyDetailActivit";
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.goods)
    RecyclerView goods;
    @BindView(R.id.receipt)
    RelativeLayout receipt;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.receiptName)
    TextView receiptName;
    @BindView(R.id.receiptPhone)
    TextView receiptPhone;
    @BindView(R.id.receiveOrder)
    Button receiveOrder;
    @BindView(R.id.receiveGood)
    Button receiveGood;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.storeName)
    TextView storeName;
    @BindView(R.id.proxyPrice)
    TextView proxyPrice;


    Order order;
    Recipient userRecipient;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_proxy_detail;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.titlebar;
    }

    @Override
    protected void initView() {
        order = Constants.order;
        userRecipient = order.getRecipient();
        Log.e(TAG, "initView: " + CommonUtil.gson.toJson(order) );
        getWindow().setStatusBarColor(getResources().getColor(R.color.douban_blue_80_percent));
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        initRecipient();
        if (order.getStore() != null) storeName.setText(order.getStore().getStorename());
        totalPrice.setText(String.valueOf(order.getGoodsprice() + order.getProxyprice()));
        proxyPrice.setText(String.valueOf(order.getProxyprice()));
        receiveOrder.setOnClickListener((v -> {
            order.setStatus(Order.Status.BUYING);
            order.setProxy(Constants.user);
            Network.getInstance().modifyOrderById(order.getId(), order).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(order) );
                    Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(response.body()) );
                    finish();
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        }));
        receiveGood.setOnClickListener((v -> {
            order.setStatus(Order.Status.COMPLETED);
            Network.getInstance().modifyOrderById(order.getId(), order).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Log.e(TAG, "onResponse: " + CommonUtil.gson.toJson(response.body()) );
                    finish();
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        }));
    }

    private void initRecipient() {
        address.setText(userRecipient.getAddress().getAddress() + " " + userRecipient.getDetaillocation());
        receiptName.setText(userRecipient.getContact());
        receiptPhone.setText(userRecipient.getPhone());
    }

    @Override
    protected void initData() {
        if (order.getStatus().equals(Order.Status.WAITING)) receiveOrder.setVisibility(View.VISIBLE);
        else receiveGood.setVisibility(View.VISIBLE);
        Log.e(TAG, "initData: " + CommonUtil.gson.toJson(order));
        goods.setAdapter(new OrderGoodAdapter(getContext(), prepareGood()));
    }

    private List<Good> prepareGood() {
        return order.getGoods();
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
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }
}
