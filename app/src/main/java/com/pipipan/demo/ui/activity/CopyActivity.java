package com.pipipan.demo.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.widget.XCollapsingToolbarLayout;

import butterknife.BindView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public class CopyActivity extends MyActivity implements XCollapsingToolbarLayout.OnScrimsListener {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ctl_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_copy;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {
        ImmersionBar.setTitleBar(getActivity(), toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.douban_blue_80_percent));
        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);

    }

    @Override
    protected void initData() {

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