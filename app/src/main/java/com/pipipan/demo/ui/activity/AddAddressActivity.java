package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Address;

import butterknife.BindView;

public class AddAddressActivity extends MyActivity {
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
        }
    }

    @Override
    protected void initView() {
        address.setOnClickListener((v -> {
            Intent intent = new Intent(getContext(), SelectAddressByMapActivity.class);
            startActivityForResult(intent, 1);
        }));
        submit.setOnClickListener((v -> {
            Gson gson = new Gson();
            Address address = new Address();
            address.setReceiptName(name.getText().toString());
            address.setAddressLocation(this.address.getText().toString());
            address.setReceiptPhone(phone.getText().toString());
            address.setDetailLocation(detail_address.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("address", gson.toJson(address));
            //TODO 发送请求进行保存
            setResult(1, intent);
            finish();
        }));
    }

    @Override
    protected void initData() {

    }
}
