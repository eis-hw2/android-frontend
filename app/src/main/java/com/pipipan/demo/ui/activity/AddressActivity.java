package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.RecipientAddress;
import com.pipipan.demo.ui.adapter.AddressAdapter;
import com.pipipan.demo.common.MyActivity;

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

    private List<RecipientAddress> getAddresses() {
        //TODO 调用API得到地址信息
        List<RecipientAddress> recipientAddresses = new ArrayList<>();
        RecipientAddress recipientAddress = new RecipientAddress();
        Address address = new Address("上海交通大学西11宿舍", 0.0, 0.0);
        recipientAddress.setAddress(address);
        recipientAddress.setDetailLocation("4楼 414寝室");
        recipientAddress.setRecipient("皮皮潘");
        recipientAddress.setPhone("18317126628");
        recipientAddresses.add(recipientAddress);
        return recipientAddresses;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1){
            Gson gson = new Gson();
            String address = data.getStringExtra("address");
            if (!(address == null ))
                addressAdapter.addItem(0, gson.fromJson(data.getStringExtra("recipientAddress"), RecipientAddress.class));
        }
    }
}
