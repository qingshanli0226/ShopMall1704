package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.bean.HomepageBean;
import com.example.net.Constant;
import com.example.shopmall.R;

import java.util.List;

public class SeckillItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final List<HomepageBean.ResultBean.SeckillInfoBean.ListBean> list_bean;

    public SeckillItemAdapter(Context context, List<HomepageBean.ResultBean.SeckillInfoBean.ListBean> list_bean) {
        this.context = context;
        this.list_bean = list_bean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_seckill_inflate, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeliest.getLikeliest(position);
            }
        });
    }

    Likeliest likeliest;

    interface Likeliest{
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

    @Override
    public int getItemCount() {
        return list_bean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_seckill_inflate_figure;
        private TextView tv_seckill_inflate_cover_price;
        private TextView tv_seckill_inflate_origin_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_seckill_inflate_figure = itemView.findViewById(R.id.iv_seckill_inflate_figure);
            tv_seckill_inflate_cover_price = itemView.findViewById(R.id.tv_seckill_inflate_cover_price);
            tv_seckill_inflate_origin_price = itemView.findViewById(R.id.tv_seckill_inflate_origin_price);
        }

        @SuppressLint("SetTextI18n")
        private void setData(final int position) {
            HomepageBean.ResultBean.SeckillInfoBean.ListBean listBean = list_bean.get(position);
            tv_seckill_inflate_cover_price.setText("￥" + listBean.getCover_price());
            tv_seckill_inflate_origin_price.setText("￥" + listBean.getOrigin_price());
            Glide.with(context).load(Constant.BASE_URL_IMAGE +listBean.getFigure()).into(iv_seckill_inflate_figure);
        }
    }

}
