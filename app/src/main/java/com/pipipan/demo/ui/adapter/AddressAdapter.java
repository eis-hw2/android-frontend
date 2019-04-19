package com.pipipan.demo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pipipan.demo.R;
import com.pipipan.demo.common.MyRecyclerViewAdapter;
import com.pipipan.demo.domain.Recipient;

import java.util.List;


public class AddressAdapter extends MyRecyclerViewAdapter<Recipient, AddressAdapter.AddressViewHolder> {
    public AddressAdapter(Context context, List<Recipient> recipientList)  {
        super(context);
        setData(recipientList);
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_address, viewGroup, false);
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder addressViewHolder, int i) {
        Recipient recipient = getItem(i);
        addressViewHolder.addressLocation.setText(recipient.getAddress().getAddressLocationName());
        addressViewHolder.detailLocation.setText(recipient.getDetailLocation());
        addressViewHolder.receiptPhone.setText(recipient.getPhone());
        addressViewHolder.receiptName.setText(recipient.getRecipient());
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
