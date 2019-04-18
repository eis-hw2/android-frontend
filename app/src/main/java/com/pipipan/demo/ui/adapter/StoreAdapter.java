package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.ui.activity.GoodActivity;

import java.util.List;

public class StoreAdapter extends MyRecyclerViewAdapter<Store, StoreAdapter.StoreViewHolder> {
    public StoreAdapter(Context context, List<Store> stores) {
        super(context);
        setData(stores);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.viewholder_store, viewGroup, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder storeViewHolder, int i) {
        Store store = getItem(i);
        //TODO 完成对应的信息提取
        storeViewHolder.view.setOnClickListener((v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), GoodActivity.class);
            intent.putExtra(GoodActivity.STOREID, String.valueOf(i));
            getContext().startActivity(intent);
        }));
    }

    class StoreViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        View view;
        StoreViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }
}
