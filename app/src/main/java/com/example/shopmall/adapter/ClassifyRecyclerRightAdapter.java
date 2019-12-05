package com.example.shopmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopmall.R;
import com.example.shopmall.bean.ClassifyBean;

import java.util.ArrayList;

public class ClassifyRecyclerRightAdapter extends RecyclerView.Adapter<ClassifyRecyclerRightAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ClassifyBean.ResultBean.HotProductListBean> hotProductListBeans;

    public ClassifyRecyclerRightAdapter(Context mContext, ArrayList<ClassifyBean.ResultBean.HotProductListBean> hotProductListBeans) {
        this.mContext = mContext;
        this.hotProductListBeans = hotProductListBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ordinary_right, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(hotProductListBeans.get(position).getFigure()).into(holder.iv_ordinary_right);
        holder.tv_ordinary_right.setText( "￥" + hotProductListBeans.get(position).getCover_price());
        holder.tv_ordinary_right.setTextColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        return hotProductListBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_ordinary_right;
        public TextView tv_ordinary_right;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_ordinary_right = itemView.findViewById(R.id.iv_ordinary_right);
            tv_ordinary_right = itemView.findViewById(R.id.tv_ordinary_right);

        }
    }
}
