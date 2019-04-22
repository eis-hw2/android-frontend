package com.pipipan.demo.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hjq.bar.TitleBar;
import com.pipipan.demo.R;
import com.pipipan.demo.common.Constants;
import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.NullRecipient;
import com.pipipan.demo.domain.Recipient;
import com.pipipan.demo.helper.CommonUtil;
import com.pipipan.demo.network.Network;
import com.pipipan.demo.ui.adapter.AddressAdapter;
import com.pipipan.demo.common.MyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends MyActivity{
    private static final String TAG = "AddressActivity";
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
        addressAdapter = new AddressAdapter(getContext(), getAddresses(), getIntent().getBooleanExtra("isSelectAddress", false));
        if (getIntent().getBooleanExtra("isSelectAddress", false)) addressAdapter.setItemListener(addressAdapter.new ItemListener() {
            @Override
            public void onItemClicked(Recipient recipient) {
                Intent intent = new Intent();
                intent.putExtra("recipient", CommonUtil.gson.toJson(recipient));
                setResult(0, intent);
                finish();
            }
        });
        recyclerView.setAdapter(addressAdapter);
    }

    private List<Recipient> getAddresses() {
        return Constants.user.getAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1){
            Gson gson = new Gson();
            String address = data.getStringExtra("recipientAddress");
            if (!(address == null )){
                Network.getInstance().addRecipient(Constants.user.getId(), CommonUtil.gson.fromJson(data.getStringExtra("recipientAddress"), Recipient.class)).enqueue(new Callback<Recipient>() {
                    @Override
                    public void onResponse(Call<Recipient> call, Response<Recipient> response) {
                        Recipient recipient = response.body();
                        Log.e(TAG, "onResponse: " + gson.toJson(recipient));
                        addressAdapter.addItem(0, recipient);
                    }
                    @Override
                    public void onFailure(Call<Recipient> call, Throwable t) {

                    }
                });
            }
        }
    }
}
