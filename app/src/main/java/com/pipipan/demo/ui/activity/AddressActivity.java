package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.adapter.AddressAdapter;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddressActivity extends MyActivity{
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.addresses)
    RecyclerView recyclerView;
    @BindView(R.id.addAddress)
    TextView addAddress;

    AddressAdapter addressAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.titlebar;
    }

    @Override
    protected void initView() {
        titleBar.setTitle("收货地址");
        addAddress.setOnClickListener((v -> {
            startActivityForResult(new Intent(getContext(), AddAddressActivity.class), 1);
        }));
    }

    @Override
    protected void initData() {
        addressAdapter = new AddressAdapter(getContext(), getAddresses());
        recyclerView.setAdapter(addressAdapter);
    }

    private List<Address> getAddresses() {
        //TODO 调用API得到地址信息
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setAddressLocation("上海交通大学西11宿舍");
        address.setDetailLocation("4楼 414寝室");
        address.setReceiptName("皮皮潘");
        address.setReceiptPhone("18317126628");
        addresses.add(address);
        addresses.add(address);
        return addresses;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1)
        addressAdapter.addData(getAddresses());
    }
}
