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

public class GoodAdapter extends MyRecyclerViewAdapter<Good, GoodAdapter.GoodViewHolder> {
    OrderListener orderListener;
    public GoodAdapter(Context context, List<Good> goods) {
        super(context);
        setData(goods);
    }

    @NonNull
    @Override
    public GoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_good, viewGroup, false);
        return new GoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodViewHolder goodViewHolder, int i) {
        Good good = getItem(i);
        goodViewHolder.add.setOnClickListener((v -> {
            int count = Integer.valueOf(goodViewHolder.count.getText().toString());
            if (count == 0){
                orderListener.onGoodItemAdd(good);
                goodViewHolder.count.setText("1");
            }
        }));
        goodViewHolder.minus.setOnClickListener((v -> {
            int count = Integer.valueOf(goodViewHolder.count.getText().toString());
            if (count == 1){
                orderListener.onGoodItemMinus(good);
                goodViewHolder.count.setText("0");
            }
        }));
    }

    class GoodViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        View view;
        ImageView add;
        ImageView minus;
        TextView count;
        public GoodViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.add = itemView.findViewById(R.id.add);
            this.minus = itemView.findViewById(R.id.minus);
            this.count = itemView.findViewById(R.id.count);
        }
    }

    public void setOrderListener(OrderListener orderListener) {
        this.orderListener = orderListener;
    }

    public abstract class OrderListener{
        public abstract void onGoodItemAdd(Good good);
        public abstract void onGoodItemMinus(Good good);
    }
}
