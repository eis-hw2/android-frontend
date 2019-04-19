package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pipipan.demo.R;
import com.pipipan.demo.common.MyActivity;
import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.Recipient;

import butterknife.BindView;

import static com.pipipan.demo.helper.CommonUtil.gson;

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

    Address locationAddress;

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
                locationAddress = gson.fromJson(data.getStringExtra("address"), Address.class);
                address.setText(locationAddress.getAddressLocationName());
                Log.d(TAG, "onActivityResult: longitude " + locationAddress.getLongitude());
        }
    }

    @Override
    protected void initView() {
        address.setOnClickListener((v -> {
            Intent intent = new Intent(getContext(), SelectAddressByMapActivity.class);
            startActivityForResult(intent, 1);
        }));
        submit.setOnClickListener((v -> { ;
            Intent intent = new Intent();
            intent.putExtra("recipientAddress", gson.toJson(buildRecipientAddress()));
            //TODO 发送请求进行保存
            Log.d(TAG, "initView: recipientAddress" + gson.toJson(buildRecipientAddress()));
            setResult(1, intent);
            finish();
        }));
    }

    private Recipient buildRecipientAddress(){
        Recipient recipient = new Recipient();
        recipient.setRecipient(name.getText().toString());
        recipient.setAddress(locationAddress);
        recipient.setPhone(phone.getText().toString());
        recipient.setDetailLocation(detail_address.getText().toString());
        return recipient;
    }

    @Override
    protected void initData() {

    }
}
