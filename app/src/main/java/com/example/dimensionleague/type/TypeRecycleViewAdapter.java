package com.example.dimensionleague.type;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buy.activity.GoodsActiviy;
import com.example.common.IntentUtil;
import com.example.dimensionleague.R;
import com.example.common.TypeBean;
import com.example.net.AppNetConfig;

import java.util.ArrayList;

public class TypeRecycleViewAdapter extends RecyclerView.Adapter<TypeRecycleViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TypeBean.ResultBean.HotProductListBean> hotProductListBeans;

    public TypeRecycleViewAdapter(Context mContext, ArrayList<TypeBean.ResultBean.HotProductListBean> hotProductListBeans) {
        this.mContext = mContext;
        this.hotProductListBeans = hotProductListBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.type_right_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext).load(AppNetConfig.BASE_URl_IMAGE + hotProductListBeans.get(position).getFigure()).into(holder.iv_ordinary_right);
        holder.tv_ordinary_right.setText( "￥" + hotProductListBeans.get(position).getCover_price());
        holder.tv_ordinary_right.setTextColor(Color.RED);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkedlist.getLinkedlist(position);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(holder.itemView.getContext(), GoodsActiviy.class);
                        intent.putExtra(IntentUtil.SHOW_GOOD,hotProductListBeans.get(position));
                        holder.itemView.getContext().startActivity(intent);
                    }
                });

            }
        });

    }

    Linkedlist linkedlist;

    interface Linkedlist{
        public void getLinkedlist(int position);
    }

    public void setLinkedlist(Linkedlist linkedlist) {
        this.linkedlist = linkedlist;
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

            iv_ordinary_right = itemView.findViewById(R.id.type_right_iv_ordinary_right);
            tv_ordinary_right = itemView.findViewById(R.id.type_right_tv_ordinary_right);

        }
    }

}
