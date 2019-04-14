package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pipipan.base.BaseFragmentAdapter;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyLazyFragment;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Store;

import java.util.List;

public class StoreAdapter extends MyRecyclerViewAdapter<Store, StoreAdapter.StoreViewHolder> {
    public StoreAdapter(Context context, List<Store> stores) {
        super(context);
        setData(stores);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.store_viewholder, viewGroup, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder storeViewHolder, int i) {
        Store store = getItem(i);
        //TODO 完成对应的信息提取
    }

    class StoreViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        StoreViewHolder(View view) {
            super(view);
        }
    }
}
