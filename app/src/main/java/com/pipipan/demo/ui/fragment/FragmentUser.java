package com.pipipan.demo.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.ui.activity.LoginActivity;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import butterknife.BindView;

public class FragmentUser extends MyLazyFragment
                            implements XCollapsingToolbarLayout.OnScrimsListener{
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login)
    Button button;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;

    static FragmentUser fragmentUser = null;
    public static FragmentUser getInstance(){
        if (fragmentUser == null){
            synchronized (FragmentUserLogin.class){
                if (fragmentUser == null)
                    fragmentUser = new FragmentUser();
            }
        }
        return fragmentUser;
    }

    @Override
    protected void initFragment() {
        super.initFragment();

        getStatusBarConfig().statusBarDarkFont(false).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
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
        button.setOnClickListener((e)->{
            startActivity(LoginActivity.class);
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onScrimsStateChange(boolean shown) {
        // CollapsingToolbarLayout 发生了渐变
        if (shown) {
            titleBar.setTitle("我的");
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
