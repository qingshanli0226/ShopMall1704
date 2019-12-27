package com.example.shopmall.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.net.Constant;
import com.example.shopmall.R;

import java.util.List;
import java.util.Map;

public class CollectionAdapter extends BaseAdapter<Map<String,String>, CollectionAdapter.ViewHolder> {
    private Context context;

    public CollectionAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_collection;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, List<Map<String, String>> data, final int position) {
        Map<String, String> map = data.get(position);
        Glide.with(context)
                .load(Constant.BASE_URL_IMAGE+map.get("img"))
                .into(holder.ivGov);
        holder.tvTitle.setText(map.get("title"));
        holder.tvPrice.setText("￥"+map.get("price"));

        holder.llCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        LinearLayout llCollection = itemView.findViewById(R.id.ll_app_collection);
        ImageView ivGov = itemView.findViewById(R.id.iv_app_gov);
        TextView tvTitle = itemView.findViewById(R.id.tv_app_title);
        TextView tvPrice = itemView.findViewById(R.id.tv_app_price);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
