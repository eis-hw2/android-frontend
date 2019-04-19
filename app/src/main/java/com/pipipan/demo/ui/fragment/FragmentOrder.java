package com.pipipan.demo.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.common.UIActivity;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.ui.adapter.OrderAdapter;
import com.pipipan.image.ImageLoader;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目框架使用示例
 */
public class FragmentOrder extends MyLazyFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.orders)
    RecyclerView orders;

    OrderAdapter orderAdapter;

    public static FragmentOrder newInstance() {
        return new FragmentOrder();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_c_title;
    }

    @Override
    protected void initView() {
        initRefreshLayout();
    }

    @Override
    protected void initData() {
        orderAdapter = new OrderAdapter(getContext(), initializeData());
        orders.setAdapter(orderAdapter);
    }

    private List<Order> initializeData() {
        List<Order> res = new ArrayList<>();
        for (int i=0; i<10; ++i){
            res.add(new Order());
        }
        return res;
    }

    private void initRefreshLayout() {
        refreshLayout.setRefreshHeader(new DeliveryHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //refreshlayout.finishRefresh(true);//传入false表示刷新失败
                refreshlayout.finishLoadMore(500);//传入false表示加载失败
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

}