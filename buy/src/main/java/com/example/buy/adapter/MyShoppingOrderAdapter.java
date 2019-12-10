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
                .into(holder.iv_gov);
        holder.tv_title.setText(map.get("title"));
        String price = map.get("price");
        String num = map.get("num");
        holder.tv_price.setText("￥" + price + ".00");
        holder.tv_orderitemnum.setText(num);

        int x = Integer.parseInt(price);
        int y = Integer.parseInt(num);
        int z = x * y;

        holder.tv_itemprice.setText("￥" + z + ".00");
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_gov = itemView.findViewById(R.id.iv_gov);
        TextView tv_title = itemView.findViewById(R.id.tv_title);
        TextView tv_price = itemView.findViewById(R.id.tv_price);
        TextView tv_orderitemnum = itemView.findViewById(R.id.tv_orderitemnum);
        TextView tv_itemprice = itemView.findViewById(R.id.tv_itemprice);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
