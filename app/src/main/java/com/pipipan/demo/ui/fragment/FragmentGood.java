package com.pipipan.demo.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.activity.OrderCheckoutActivity;
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
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.checkout)
    TextView checkout;

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
        checkout.setEnabled(false);
        checkout.setOnClickListener((v -> {
            Intent intent = new Intent(getContext(), OrderCheckoutActivity.class);
            intent.putExtra("order", CommonUtil.gson.toJson(order));
            startActivity(intent);
        }));
    }

    @Override
    protected void initData() {
        order = Order.createOrder();
        goodAdapter = new GoodAdapter(getContext(), getGoodList());
        goodAdapter.setOrderListener(goodAdapter.new OrderListener(){
            @Override
            public void onGoodItemAdd(Good good) {
                List<Good> goods = order.getGoods();
                goods.add(good);
                order.setGoodsprice(order.getGoodsprice() + good.getPrice());
                StringBuilder sb = new StringBuilder("");
                sb.append("商品总价 ");
                sb.append("￥");
                sb.append(order.getGoodsprice());
                totalPrice.setText(sb.toString());
                if (order.getGoodsprice() >= 15){
                    checkout.setBackgroundColor(getResources().getColor(R.color.light_green));
                    checkout.setText("去结算");
                    checkout.setTextColor(getResources().getColor(R.color.white80));
                    checkout.setEnabled(true);
                }
                Log.e(TAG, "onGoodItemAdd: " + CommonUtil.gson.toJson(order));
            }

            @Override
            public void onGoodItemMinus(Good good) {
                List<Good> goods = order.getGoods();
                goods.remove(good);
                order.setGoodsprice(order.getGoodsprice() - good.getPrice());
                StringBuilder sb = new StringBuilder("");
                sb.append("￥");
                sb.append(order.getGoodsprice());
                totalPrice.setText(sb.toString());
                if (order.getGoodsprice() < 15){
                    checkout.setBackgroundColor(getResources().getColor(R.color.douban_gray_55_percent));
                    checkout.setText("￥15起送");
                    checkout.setEnabled(false);
                    if (goods.isEmpty()) {
                        totalPrice.setText("未选购商品");
                    }
                }
                Log.e(TAG, "onGoodItemMinus: " + CommonUtil.gson.toJson(order) );
            }
        });
        goods.setAdapter(goodAdapter);
    }

    private List<Good> getGoodList() {
        List<Good> goods = new ArrayList<>();
        for (int i=0; i<10; ++i){
            Good good = new Good();
            good.setPrice(10);
            goods.add(good);
        }
        return goods;
    }
}
