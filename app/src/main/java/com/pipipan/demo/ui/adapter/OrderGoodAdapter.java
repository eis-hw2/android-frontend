package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;

import java.util.List;

public class OrderGoodAdapter extends MyRecyclerViewAdapter<Good, OrderGoodAdapter.OrderGoodViewHolder> {
    public OrderGoodAdapter(Context context, List<Good> goods) {
        super(context);
        setData(goods);
    }

    @NonNull
    @Override
    public OrderGoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_order_good, viewGroup, false);
        return new OrderGoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderGoodViewHolder orderViewHolder, int i) {

    }

    class OrderGoodViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        View view;
        ImageView image;
        TextView money;
        public OrderGoodViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.image = itemView.findViewById(R.id.image);
            this.money = itemView.findViewById(R.id.money);
        }
    }
}
