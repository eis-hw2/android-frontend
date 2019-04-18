package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.common.LogThreadPoolManager;
import com.google.gson.Gson;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.RecipientAddress;

import butterknife.BindView;

public class AddAddressActivity extends MyActivity {
    private static final String TAG = "AddAddressActivity";
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.detail_address)
    EditText detail_address;
    @BindView(R.id.submit)
    Button submit;

    Double longitude = 0.0;
    Double latitude = 0.0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.titlebar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                address.setText(data.getStringExtra("address"));
                longitude = data.getDoubleExtra("longitude", -1);
                latitude = data.getDoubleExtra("latitude", -1);
                Log.d(TAG, "onActivityResult: longitude " + data.getDoubleExtra("longitude", -1));
        }
    }

    @Override
    protected void initView() {
        address.setOnClickListener((v -> {
            Intent intent = new Intent(getContext(), SelectAddressByMapActivity.class);
            startActivityForResult(intent, 1);
        }));
        submit.setOnClickListener((v -> { ;
            Gson gson = new Gson();
            RecipientAddress recipientAddress = new RecipientAddress();
            recipientAddress.setRecipient(name.getText().toString());
            Address address = new Address(this.address.getText().toString(), longitude, latitude);
            recipientAddress.setAddress(address);
            recipientAddress.setPhone(phone.getText().toString());
            recipientAddress.setDetailLocation(detail_address.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("recipientAddress", gson.toJson(recipientAddress));
            //TODO 发送请求进行保存
            Log.d(TAG, "initView: recipientAddress" + gson.toJson(recipientAddress));
            setResult(1, intent);
            finish();
        }));
    }

    @Override
    protected void initData() {

    }
}
