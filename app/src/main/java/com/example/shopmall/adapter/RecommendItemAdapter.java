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
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.HomepageBean;

import java.util.List;

public class RecommendItemAdapter extends RecyclerView.Adapter<RecommendItemAdapter.ViewHolder> {
    private Context context;
    private List<HomepageBean.ResultBean.RecommendInfoBean> recommend_info_bean;

    public RecommendItemAdapter(Context context, List<HomepageBean.ResultBean.RecommendInfoBean> recommend_info_bean) {
        this.context = context;
        this.recommend_info_bean = recommend_info_bean;
    }

    @NonNull
    @Override
    public RecommendItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_inflate, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecommendItemAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(Constant.BASE_URL_IMAGE + recommend_info_bean.get(position).getFigure()).into(holder.iv_recommend_figure);
        holder.tv_recommend_name.setText(recommend_info_bean.get(position).getName());
        holder.tv_recommend_price.setText("￥" + recommend_info_bean.get(position).getCover_price());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return recommend_info_bean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_recommend_figure;
        private TextView tv_recommend_name;
        private TextView tv_recommend_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_recommend_figure = itemView.findViewById(R.id.iv_recommend_figure);
            tv_recommend_name = itemView.findViewById(R.id.tv_recommend_name);
            tv_recommend_price = itemView.findViewById(R.id.tv_recommend_price);

        }
    }
}
