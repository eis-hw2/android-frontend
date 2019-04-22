package com.pipipan.demo.ui.activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
        import com.pipipan.demo.R;
        import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.adapter.OrderGoodAdapter;
import com.pipipan.demo.widget.MarqueTextView;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderUserDetailActivity extends MyActivity implements XCollapsingToolbarLayout.OnScrimsListener {
    private static final String TAG = "OrderUserDetailActivity";

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.address)
    MarqueTextView address;
    @BindView(R.id.contactProxy)
    Button contackParoxy;
    @BindView(R.id.changeOrder)
    Button changeOrder;
    @BindView(R.id.goods)
    RecyclerView goods;
    @BindView(R.id.title)
    TextView title;

    Order order;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_user_detail;
    }

    @Override
    protected int getTitleBarId(){
        return R.id.titlebar;
    }

    @Override
    protected void initView() {
        address.setSelected(true);
    }

    @Override
    protected void initData() {
        order = CommonUtil.gson.fromJson(getIntent().getStringExtra("order"), Order.class);
        if (order.getStatus().equals(Order.Status.WAITING)) changeOrder.setVisibility(View.VISIBLE);
        else contackParoxy.setVisibility(View.VISIBLE);
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
