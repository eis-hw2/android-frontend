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
    boolean isSelectAddress;
    ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public AddressAdapter(Context context, List<Recipient> recipientList, boolean isSelectAddress)  {
        super(context);
        setData(recipientList);
        this.isSelectAddress = isSelectAddress;
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
        addressViewHolder.addressLocation.setText(recipient.getAddress().getAddress());
        addressViewHolder.detailLocation.setText(recipient.getDetaillocation());
        addressViewHolder.receiptPhone.setText(recipient.getPhone());
        addressViewHolder.receiptName.setText(recipient.getContact());
        if (isSelectAddress) {
            addressViewHolder.view.setOnClickListener((v -> {
                itemListener.onItemClicked(recipient);
            }));
        }
    }

    class AddressViewHolder extends MyRecyclerViewAdapter.ViewHolder{
        TextView addressLocation;
        TextView detailLocation;
        TextView receiptName;
        TextView receiptPhone;
        View view;
        public AddressViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            addressLocation = itemView.findViewById(R.id.addressLocation);
            detailLocation = itemView.findViewById(R.id.detailLocation);
            receiptName = itemView.findViewById(R.id.receiptName);
            receiptPhone = itemView.findViewById(R.id.receiptPhone);
        }
    }

    public abstract class ItemListener{
        public abstract void onItemClicked(Recipient recipient);
    }
}
