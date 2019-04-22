package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.ui.activity.OrderProxyDetailActivity;
import com.pipipan.demo.ui.activity.OrderUserDetailActivity;

import java.util.List;

public class OrderAdapter extends MyRecyclerViewAdapter<Order, OrderAdapter.OrderViewHolder> {
    public OrderAdapter(Context context, List<Order> orders) {
        super(context);
        setData(orders);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_order, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {
        Order order = getItem(i);
        if (order.getStore() != null) orderViewHolder.storeName.setText(order.getStore().getStorename());
        orderViewHolder.good.setText(String.valueOf(order.getGoods().get(0).getGoodname()) + "等物品");
        orderViewHolder.money.setText("￥" + String.valueOf(order.getGoodsprice() + order.getProxyprice()));
        switch (order.getStatus()){
            case WAITING:
                orderViewHolder.status.setText("待接单");
                break;
            case BUYING:
                orderViewHolder.status.setText("待送达");
                break;
            case COMPLETED:
                orderViewHolder.status.setText("已完成");
                break;
        }
        orderViewHolder.view.setOnClickListener((v -> {
            Constants.order = order;
            Log.e("orderId", "onBindViewHolder: " + String.valueOf(order.getBuyer().getId()) + " " + Constants.user.getId());
            if (!order.getBuyer().getId().equals(Constants.user.getId())) {
                Intent intent = new Intent(getContext(), OrderProxyDetailActivity.class);
                getContext().startActivity(intent);
            }
            else {
                Intent intent = new Intent(getContext(), OrderUserDetailActivity.class);
                getContext().startActivity(intent);
            }
        }));
    }

    class OrderViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        View view;
        TextView status;
        TextView storeName;
        TextView good;
        TextView money;
        public OrderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            status = itemView.findViewById(R.id.status);
            storeName = itemView.findViewById(R.id.storeName);
            good = itemView.findViewById(R.id.good);
            money = itemView.findViewById(R.id.money);
        }
    }
}
