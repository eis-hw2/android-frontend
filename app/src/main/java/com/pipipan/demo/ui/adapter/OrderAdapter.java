package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Order;

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
    }

    class OrderViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        View view;
        public OrderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
