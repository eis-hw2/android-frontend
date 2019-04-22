package com.pipipan.demo.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hjq.bar.TitleBar;
import com.pipipan.base.BaseRecyclerViewAdapter;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.activity.OrderProxyDetailActivity;
import com.pipipan.demo.ui.adapter.OrderAdapter;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目框架使用示例
 */
public abstract class FragmentOrder extends MyLazyFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.orders)
    RecyclerView orders;
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.view)
    Button view;

    OrderAdapter orderAdapter;

    public static FragmentOrder createUserOrderFragment() {
        return new UserOrderFragment();
    }

    public static FragmentOrder createLocalOrderFragment() {
        return new LocalOrderFragment();
    }

    public static FragmentOrder createUnCompleteOrderFragment() {
        return new UncompleteOrderFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        initRefreshLayout();
        initProxyOrderView();
    }

    public void initProxyOrderView(){

    }

    @Override
    protected void initData() {
        orderAdapter = new OrderAdapter(getContext(), new ArrayList<>());
        orders.setAdapter(orderAdapter);
        refreshData();
    }

    private void initRefreshLayout() {
        refreshLayout.setRefreshHeader(new DeliveryHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //orderAdapter.setData(initOrderData());
                refreshData();
                refreshlayout.finishRefresh(true);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(500);//传入false表示加载失败
            }
        });
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    public abstract void refreshData();
}