package com.example.buy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buy.R;
import com.example.framework.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyShoppingOrderAdapter extends BaseAdapter<Map<String, String>, MyShoppingOrderAdapter.ViewHolder> {


    private Context context;

    public MyShoppingOrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_shop_order;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, List<Map<String, String>> data, int position) {
        Map<String, String> map = data.get(position);
        Glide.with(context)
                .load(map.get("img"))
                .into(holder.ivGov);
        holder.tvTitle.setText(map.get("title"));
        String price = map.get("price");
        String num = map.get("num");
        holder.tvPrice.setText("￥" + price);
        holder.tvOrderitemnum.setText(num);

        double x = Double.parseDouble(price);
        double y = Double.parseDouble(num);
        double z = x * y;

        holder.tvItemprice.setText("￥" + z + "0");
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageView ivGov = itemView.findViewById(R.id.iv_buy_gov);
        TextView tvTitle = itemView.findViewById(R.id.tv_buy_title);
        TextView tvPrice = itemView.findViewById(R.id.tv_buy_price);
        TextView tvOrderitemnum = itemView.findViewById(R.id.tv_buy_orderitemnum);
        TextView tvItemprice = itemView.findViewById(R.id.tv_buy_itemprice);

    }

}
