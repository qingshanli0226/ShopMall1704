package com.example.shoppingcart.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingcart.bean.ShoppingCartBean;
import com.example.shoppingcart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvAdp extends RecyclerView.Adapter<RvAdp.Myhodler> {
    ArrayList<ShoppingCartBean> arr;
    Context context;

    public RvAdp(ArrayList<ShoppingCartBean> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public Myhodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.cart,null);
        return new Myhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myhodler myhodler, final int i) {
        Picasso.with(context).load(arr.get(i).getUrl());
        myhodler.tvGoodsName.setText(arr.get(i).getProductName());
        myhodler.tvGoodsPrice.setText(arr.get(i).getProductId());
        myhodler.tvNum.setText(arr.get(i).getProductNum());
        myhodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huidiao.hui(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    class Myhodler extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private ImageView ivAdapterListPic;
        private TextView tvGoodsName;
        private TextView tvGoodsPrice;
        private TextView tvTypeSize;
        private TextView tvReduce;
        private TextView tvNum;
        private TextView tvAdd;



        public Myhodler(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            ivAdapterListPic = (ImageView)  itemView.findViewById(R.id.iv_adapter_list_pic);
            tvGoodsName = (TextView)  itemView.findViewById(R.id.tv_goods_name);
            tvGoodsPrice = (TextView)  itemView.findViewById(R.id.tv_goods_price);
            tvTypeSize = (TextView)  itemView.findViewById(R.id.tv_type_size);
            tvReduce = (TextView)  itemView.findViewById(R.id.tv_reduce);
            tvNum = (TextView)  itemView.findViewById(R.id.tv_num);
            tvAdd = (TextView)  itemView.findViewById(R.id.tv_add);

        }
    }
    public interface Huidiao{
       void hui(int i);
    }
    Huidiao huidiao;

    public Huidiao getHuidiao() {
        return huidiao;
    }

    public void setHuidiao(Huidiao huidiao) {
        this.huidiao = huidiao;
    }
}

