package com.pipipan.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Address;

import java.util.List;


public class AddressAdapter extends MyRecyclerViewAdapter<Address, AddressAdapter.AddressViewHolder> {
    public AddressAdapter(Context context, List<Address> addressList)  {
        super(context);
        setData(addressList);
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_viewholder, viewGroup, false);
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder addressViewHolder, int i) {
        Address address = getItem(i);
        addressViewHolder.addressLocation.setText(address.getAddressLocation());
        addressViewHolder.detailLocation.setText(address.getDetailLocation());
        addressViewHolder.receiptPhone.setText(address.getReceiptPhone());
        addressViewHolder.receiptName.setText(address.getReceiptName());
    }

    class AddressViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        TextView addressLocation;
        TextView detailLocation;
        TextView receiptName;
        TextView receiptPhone;
        public AddressViewHolder(View itemView) {
            super(itemView);
            addressLocation = itemView.findViewById(R.id.addressLocation);
            detailLocation = itemView.findViewById(R.id.detailLocation);
            receiptName = itemView.findViewById(R.id.receiptName);
            receiptPhone = itemView.findViewById(R.id.receiptPhone);
        }
    }
}
