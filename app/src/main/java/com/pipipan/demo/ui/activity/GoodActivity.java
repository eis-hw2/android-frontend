package com.pipipan.demo.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.ui.Listener.AppBarStateChangeListener;
import com.pipipan.demo.ui.adapter.FragmentAdapter;
import com.pipipan.demo.ui.fragment.FragmentComment;
import com.pipipan.demo.ui.fragment.FragmentGood;
import com.pipipan.demo.ui.fragment.FragmentStoreInformation;
import com.pipipan.demo.ui.fragment.FragmentUser;
import com.pipipan.demo.ui.fragment.FragmentUserLogin;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class GoodActivity extends MyActivity
                            implements XCollapsingToolbarLayout.OnScrimsListener {
    public final static String STOREID = "storeID";
    Store store;
    @BindView(R.id.abl_test_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.t_test_title)
    Toolbar mToolbar;
    @BindView(R.id.tb_test_a_bar)
    TitleBar mTitleBar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.scroll)
    NestedScrollView scrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    search.setVisibility(View.GONE);
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    search.setVisibility(View.VISIBLE);
                    mTitleBar.setBackgroundColor(getResources().getColor(R.color.douban_blue_80_percent));
                }else {
                    //中间状态
                    search.setVisibility(View.GONE);
                }
            }
        });
        scrollView.setFillViewport(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.douban_blue_80_percent));
    }

    private void initViewPager() {
        List<String> titles = Arrays.asList("点餐", "评价", "商家");
        for (String title : titles){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        List<Fragment> fragments = initFragments();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private List<Fragment> initFragments(){
        return Arrays.asList(new FragmentGood(), new FragmentComment(), new FragmentStoreInformation());
    }

    @Override
    protected void initData() {
        //TODO 前后端对接得到store
        //toast(getIntent().getStringExtra(STOREID));
        initViewPager();
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

    /**
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            getStatusBarConfig().statusBarDarkFont(true).init();
        }else {
            getStatusBarConfig().statusBarDarkFont(false).init();
        }
    }
}
