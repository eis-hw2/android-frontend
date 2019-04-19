package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.domain.Recipient;
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
    @BindView(R.id.receipt)
    RelativeLayout receipt;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.receiptName)
    TextView receiptName;
    @BindView(R.id.receiptPhone)
    TextView receiptPhone;


    Order order;
    Recipient userRecipient;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_checkout;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.titlebar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                Log.e(TAG, "onActivityResult: " + data.getStringExtra("recipient"));
                userRecipient = CommonUtil.gson.fromJson(data.getStringExtra("recipient"), Recipient.class);
                initRecipient();
        }
    }

    private void initRecipient() {
        address.setText(userRecipient.getAddress().getAddressLocationName() + " " + userRecipient.getDetailLocation());
        receiptName.setText(userRecipient.getRecipient());
        receiptPhone.setText(userRecipient.getPhone());
    }

    @Override
    protected void initView() {
        receipt.setOnClickListener((v -> {
            Intent intent = new Intent(getContext(), AddressActivity.class);
            intent.putExtra("isSelectAddress", true);
            startActivityForResult(intent, 0);
        }));
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
            titleBar.setTitle("");
            getStatusBarConfig().statusBarDarkFont(true).init();
        }else {
            titleBar.setTitle("订单");
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
