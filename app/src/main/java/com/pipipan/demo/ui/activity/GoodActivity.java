package com.pipipan.demo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Store;

public class GoodActivity extends MyActivity {
    public final static String STOREID = "storeID";
    Store store;

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

    }

    @Override
    protected void initData() {
        //TODO 前后端对接得到store
        toast(getIntent().getStringExtra(STOREID));
    }
}
