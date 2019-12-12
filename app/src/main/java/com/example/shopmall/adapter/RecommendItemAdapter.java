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
import com.example.framework.bean.HomepageBean;
import com.example.net.Constant;
import com.example.shopmall.R;

import java.util.List;

public class RecommendItemAdapter extends BaseAdapter<HomepageBean.ResultBean.RecommendInfoBean,RecommendItemAdapter.ViewHolder> {

    private Context mContext;

    public RecommendItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_recommend_inflate;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, List<HomepageBean.ResultBean.RecommendInfoBean> recommendInfoBeans, int position) {

        holder.setData(recommendInfoBeans,position);

    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivRecommendFigure;
        private TextView tvRecommendName;
        private TextView tvRecommendPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivRecommendFigure = itemView.findViewById(R.id.iv_recommend_figure);
            tvRecommendName = itemView.findViewById(R.id.tv_recommend_name);
            tvRecommendPrice = itemView.findViewById(R.id.tv_recommend_price);

        }

        @SuppressLint("SetTextI18n")
        public void setData(List<HomepageBean.ResultBean.RecommendInfoBean> recommendInfoBeans, final int position) {
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE + recommendInfoBeans.get(position).getFigure()).into(ivRecommendFigure);
            tvRecommendName.setText(recommendInfoBeans.get(position).getName());
            tvRecommendPrice.setText("￥" + recommendInfoBeans.get(position).getCover_price());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeliest.getLikeliest(position);
                }
            });
        }
    }

    private Likeliest likeliest;

    interface Likeliest{
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

}
