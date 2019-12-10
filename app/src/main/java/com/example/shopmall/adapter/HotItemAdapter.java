package com.example.shopmall.adapter;

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

public class HotItemAdapter extends RecyclerView.Adapter<HotItemAdapter.ViewHolder> {

    private Context context;
    private List<HomepageBean.ResultBean.HotInfoBean> hot_info_bean;

    public HotItemAdapter(Context context, List<HomepageBean.ResultBean.HotInfoBean> hot_info_bean) {
        this.context = context;
        this.hot_info_bean = hot_info_bean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot_inflate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load(Constant.BASE_URL_IMAGE + hot_info_bean.get(position).getFigure()).into(holder.iv_hot_figure);
        holder.tv_hot_name.setText(hot_info_bean.get(position).getName());
        holder.tv_hot_cover_price.setText("￥" + hot_info_bean.get(position).getCover_price());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeliest.getLikeliest(holder,position);
            }
        });

    }

    Likeliest likeliest;

    interface Likeliest {
        public void getLikeliest(ViewHolder holder, int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

    @Override
    public int getItemCount() {
        return hot_info_bean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_hot_figure;
        private TextView tv_hot_name;
        private TextView tv_hot_cover_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_hot_figure = itemView.findViewById(R.id.iv_hot_figure);
            tv_hot_name = itemView.findViewById(R.id.tv_hot_name);
            tv_hot_cover_price = itemView.findViewById(R.id.tv_hot_cover_price);

        }
    }
}
