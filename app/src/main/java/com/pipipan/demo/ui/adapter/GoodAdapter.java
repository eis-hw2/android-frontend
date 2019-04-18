package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Good;

import java.util.List;

public class GoodAdapter extends MyRecyclerViewAdapter<Good, GoodAdapter.GoodViewHolder> {
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

    }

    class GoodViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        View view;
        public GoodViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}
