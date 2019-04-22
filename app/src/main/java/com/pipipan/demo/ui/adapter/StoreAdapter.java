package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.helper.CommonUtil;
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
        storeViewHolder.name.setText(store.getStorename());
        storeViewHolder.view.setOnClickListener((v -> {
            Constants.store = store;
            Intent intent = new Intent(getContext(), GoodActivity.class);
            intent.putExtra("store", CommonUtil.gson.toJson(store));
            getContext().startActivity(intent);
        }));
    }

    class StoreViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        View view;
        TextView name;
        StoreViewHolder(View view) {
            super(view);
            this.view = view;
            this.name = view.findViewById(R.id.name);
        }
    }
}
