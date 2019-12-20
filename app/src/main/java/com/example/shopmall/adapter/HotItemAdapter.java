package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.framework.bean.HomepageBean;

import java.util.List;

public class HotItemAdapter extends BaseAdapter<HomepageBean.ResultBean.HotInfoBean,HotItemAdapter.ViewHolder> {

    private Context mContext;

    public HotItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_hot_inflate;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, List<HomepageBean.ResultBean.HotInfoBean> hotInfoBeans, int position) {
        holder.setData(hotInfoBeans,position);
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivHotFigure;
        private TextView tvHotName;
        private TextView tvHotCoverPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivHotFigure = itemView.findViewById(R.id.iv_hot_figure);
            tvHotName = itemView.findViewById(R.id.tv_hot_name);
            tvHotCoverPrice = itemView.findViewById(R.id.tv_hot_cover_price);

        }

        @SuppressLint("SetTextI18n")
        public void setData(List<HomepageBean.ResultBean.HotInfoBean> hotInfoBeans, final int position) {
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE + hotInfoBeans.get(position).getFigure()).into(ivHotFigure);
            tvHotName.setText(hotInfoBeans.get(position).getName());
            tvHotCoverPrice.setText("￥" + hotInfoBeans.get(position).getCover_price());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeliest.getLikeliest(position);
                }
            });
        }
    }

    private Likeliest likeliest;

    interface Likeliest {
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

}
