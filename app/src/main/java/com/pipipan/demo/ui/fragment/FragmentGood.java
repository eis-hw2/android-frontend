package com.pipipan.demo.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;

import butterknife.BindView;

public class FragmentGood extends MyLazyFragment {
    static FragmentGood fragmentGood = null;

    public static FragmentGood getInstance(){
        if (fragmentGood == null){
            synchronized (FragmentUserLogin.class){
                if (fragmentGood == null)
                    fragmentGood = new FragmentGood();
            }
        }
        return fragmentGood;
    }

    @BindView(R.id.goods)
    RecyclerView goods;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_good;
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

    }
}
