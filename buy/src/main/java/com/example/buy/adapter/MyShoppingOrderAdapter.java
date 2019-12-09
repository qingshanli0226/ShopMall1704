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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyShoppingOrderAdapter extends RecyclerView.Adapter<MyShoppingOrderAdapter.ViewHolder> {

    private List<Map<String, String>> data = new ArrayList<>();

    private Context context;

    public MyShoppingOrderAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<Map<String, String>> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
    public int getItemCount() {
        return data.size();
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
