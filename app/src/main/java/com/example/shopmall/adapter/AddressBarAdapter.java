package com.example.shopmall.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;
import com.example.framework.bean.AddressBarBean;
import com.example.shopmall.R;

import java.util.List;

public class AddressBarAdapter extends BaseAdapter<AddressBarBean,AddressBarAdapter.ViewHolder> {
    @Override
    protected AddressBarAdapter.ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_address_bar_inflate;
    }

    @Override
    protected void onBindHolder(AddressBarAdapter.ViewHolder holder, List<AddressBarBean> addressBarBeans, int position) {
        holder.setData(addressBarBeans,position);
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvConsignee;
        private TextView tvCellPhoneNumber;
        private TextView tvLocationAddressBar;
        private TextView tvDetailedAddressBar;
        private ImageView ivModificationAddressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvConsignee = itemView.findViewById(R.id.tv_consignee);
            tvCellPhoneNumber = itemView.findViewById(R.id.tv_cell_phone_number);
            tvLocationAddressBar = itemView.findViewById(R.id.tv_location_address_bar);
            tvDetailedAddressBar = itemView.findViewById(R.id.tv_detailed_address_bar);
            ivModificationAddressBar = itemView.findViewById(R.id.iv_modification_address_bar);

        }

        public void setData(List<AddressBarBean> addressBarBeans, int position) {
            tvConsignee.setText(addressBarBeans.get(position).getConsignee());
            tvCellPhoneNumber.setText(addressBarBeans.get(position).getCell_phone_number());
            tvLocationAddressBar.setText(addressBarBeans.get(position).getLocation());
            tvDetailedAddressBar.setText(addressBarBeans.get(position).getDetailed_address());

            ivModificationAddressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
