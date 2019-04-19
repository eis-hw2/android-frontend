package com.pipipan.demo.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.adapter.GoodAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentGood extends MyLazyFragment {
    private static final String TAG = "FragmentGood";
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

    GoodAdapter goodAdapter;
    Order order;

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
        order = Order.createOrder();
        goodAdapter = new GoodAdapter(getContext(), getGoodList());
        goodAdapter.setOrderListener(goodAdapter.new OrderListener(){
            @Override
            public void onGoodItemAdd(Good good) {
                order.getGoods().add(good);
                order.setGoodsprice(order.getGoodsprice() + good.getPrice());
                Log.e(TAG, "onGoodItemAdd: " + CommonUtil.gson.toJson(order));
            }

            @Override
            public void onGoodItemMinus(Good good) {
                order.getGoods().remove(good);
                order.setGoodsprice(order.getGoodsprice() - good.getPrice());
                Log.e(TAG, "onGoodItemMinus: " + CommonUtil.gson.toJson(order) );
            }
        });
        goods.setAdapter(goodAdapter);
    }

    private List<Good> getGoodList() {
        List<Good> goods = new ArrayList<>();
        for (int i=0; i<10; ++i){
            goods.add(new Good());
        }
        return goods;
    }
}
