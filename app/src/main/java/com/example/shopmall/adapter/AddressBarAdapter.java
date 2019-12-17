package com.example.shopmall.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;
import com.example.framework.bean.AddressBarBean;
import com.example.shopmall.R;
import com.example.shopmall.bean.AutoLoginBean;

import java.util.List;

public class AddressBarAdapter extends BaseAdapter<AutoLoginBean.ResultBean,AddressBarAdapter.ViewHolder> {
    @Override
    protected AddressBarAdapter.ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_address_bar_inflate;
    }

    @Override
    protected void onBindHolder(AddressBarAdapter.ViewHolder holder, List<AutoLoginBean.ResultBean> resultBeans, int position) {
        holder.setData(resultBeans,position);
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

        public void setData(List<AutoLoginBean.ResultBean> resultBeans, int position) {
//            tvConsignee.setText(resultBeans.get(position).getConsignee());
            tvCellPhoneNumber.setText((String) resultBeans.get(position).getPhone());
            tvLocationAddressBar.setText((String) resultBeans.get(position).getAddress());
//            tvDetailedAddressBar.setText(resultBeans.get(position).getDetailed_address());

            ivModificationAddressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
