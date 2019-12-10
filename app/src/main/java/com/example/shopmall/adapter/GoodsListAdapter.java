package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsInfoActivity;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.bean.GoodsListBean;

import java.util.ArrayList;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GoodsListBean.ResultBean.PageDataBean> page_data_bean;

    public GoodsListAdapter(Context context, ArrayList<GoodsListBean.ResultBean.PageDataBean> page_data_bean) {
        this.context = context;
        this.page_data_bean = page_data_bean;
    }

    @NonNull
    @Override
    public GoodsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_list_inflate, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GoodsListAdapter.ViewHolder holder, final int position) {

        Glide.with(context).load(Constant.BASE_URL_IMAGE + page_data_bean.get(position).getFigure()).into(holder.iv_goods_list_figure);
        holder.tv_goods_list_name.setText(page_data_bean.get(position).getName());
        holder.tv_goods_list_cover_price.setText("￥" + page_data_bean.get(position).getOrigin_price());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cover_price = page_data_bean.get(position).getCover_price();
                String name = page_data_bean.get(position).getName();
                String figure = page_data_bean.get(position).getFigure();
                String product_id = page_data_bean.get(position).getProduct_id();
                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                Intent intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra("goods_bean",goodsBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return page_data_bean.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_goods_list_figure;
        private TextView tv_goods_list_name;
        private TextView tv_goods_list_cover_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_goods_list_figure = itemView.findViewById(R.id.iv_goods_list_figure);
            tv_goods_list_name = itemView.findViewById(R.id.tv_goods_list_name);
            tv_goods_list_cover_price = itemView.findViewById(R.id.tv_goods_list_cover_price);

        }
    }
}