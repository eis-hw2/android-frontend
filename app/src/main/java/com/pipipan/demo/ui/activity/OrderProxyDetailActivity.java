package com.pipipan.demo.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.domain.Recipient;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.adapter.OrderGoodAdapter;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        getWindow().setStatusBarColor(getResources().getColor(R.color.douban_blue_80_percent));
        ImmersionBar.setTitleBar(getActivity(), toolbar);
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        //TODO 填充收件人信息
        if (getIntent().getBooleanExtra("isWaiting", false)) receiveOrder.setVisibility(View.VISIBLE);
        else receiveGood.setVisibility(View.VISIBLE);
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
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }else {
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
