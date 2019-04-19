package com.pipipan.demo.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.adapter.OrderGoodAdapter;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderCheckoutActivity extends MyActivity implements XCollapsingToolbarLayout.OnScrimsListener{
    private static final String TAG = "OrderCheckoutActivity";
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.goods)
    RecyclerView goods;

    Order order;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_checkout;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.titlebar;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        order = CommonUtil.gson.fromJson(getIntent().getStringExtra("order"), Order.class);
        Log.e(TAG, "initData: " + CommonUtil.gson.toJson(order));
        goods.setAdapter(new OrderGoodAdapter(getContext(), prepareGood()));
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
            titleBar.setTitle("订单");
            getStatusBarConfig().statusBarDarkFont(true).init();
        }else {
            titleBar.setTitle("");
            getStatusBarConfig().statusBarDarkFont(false).init();
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
