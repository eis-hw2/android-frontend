package com.pipipan.demo.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.ui.adapter.FragmentAdapter;
import com.pipipan.demo.ui.adapter.OrderAdapter;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class FragmentFound extends MyLazyFragment implements XCollapsingToolbarLayout.OnScrimsListener{
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.scroll)
    NestedScrollView scrollView;

    OrderAdapter orderAdapter;

    public static FragmentFound newInstance() {
        return new FragmentFound();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.titlebar;
    }

    @Override
    protected void initView() {
        ImmersionBar.setTitleBar(getFragmentActivity(), toolbar);
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        scrollView.setFillViewport(true);
        initViewPager();
    }

    private void initViewPager() {
        List<String> titles = Arrays.asList("附近的订单", "待完成订单");
        for (String title : titles){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        List<Fragment> fragments = initFragments();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private List<Fragment> initFragments(){
        return Arrays.asList(FragmentOrder.createLocalOrderFragment(), FragmentOrder.createUnCompleteOrderFragment());
    }



    @Override
    protected void initData() {
        orderAdapter = new OrderAdapter(getContext(), initializeData());
    }

    private List<Order> initializeData() {
        //TODO 自己附近待接收的订单
        List<Order> res = new ArrayList<>();
        for (int i=0; i<10; ++i){
            res.add(new Order());
        }
        return res;
    }

    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            titleBar.setTitle("发现");
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
