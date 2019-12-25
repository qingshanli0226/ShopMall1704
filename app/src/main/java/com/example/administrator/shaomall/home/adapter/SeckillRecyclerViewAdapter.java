package com.example.administrator.shaomall.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.shaomall.R;
import com.shaomall.framework.bean.HomeBean;
import com.example.net.AppNetConfig;

import java.util.List;



public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private HomeBean.ResultBean.SeckillInfoBean data;
    private final List<HomeBean.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context mContext, HomeBean.ResultBean.SeckillInfoBean data) {
        this.mContext = mContext;
        this.data = data;
        list = data.getList();
    }

    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_seckill_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFigure;
        private TextView tvCoverPrice;
        private TextView tvOriginPrice;
        private LinearLayout ll_root;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivFigure = itemView.findViewById(R.id.seckill_item_iv_figure);
            tvCoverPrice = itemView.findViewById(R.id.seckill_item_tv_cover_price);
            tvOriginPrice = itemView.findViewById(R.id.seckill_item_tv_origin_price);
            ll_root = itemView.findViewById(R.id.seckill_item_ll_root);
        }

        public void setData(final int position) {
            HomeBean.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);
            tvCoverPrice.setText("￥" + listBean.getCover_price());
            tvOriginPrice.setText("￥" + listBean.getOrigin_price());
            Glide.with(mContext)
                    .load(AppNetConfig.BASE_URl_IMAGE +listBean.getFigure())
                    .into(ivFigure);
            ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
                    if (onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onClick(position);
                    }
                }
            });
        }
    }

    public interface OnSeckillRecyclerView {
        void onClick(int position);
    }

    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;
}
