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
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.ClassifyBean;

import java.util.ArrayList;

/**
 * 热卖推荐
 */
public class ClassifyRecyclerRightAdapter extends RecyclerView.Adapter<ClassifyRecyclerRightAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ClassifyBean.ResultBean.HotProductListBean> hot_product_list_bean;

    public ClassifyRecyclerRightAdapter(Context context, ArrayList<ClassifyBean.ResultBean.HotProductListBean> hot_product_list_bean) {
        this.context = context;
        this.hot_product_list_bean = hot_product_list_bean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ordinary_right, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(Constant.BASE_URL_IMAGE + hot_product_list_bean.get(position).getFigure()).into(holder.iv_ordinary_right);
        holder.tv_ordinary_right.setText( "￥" + hot_product_list_bean.get(position).getCover_price());
        holder.tv_ordinary_right.setTextColor(Color.RED);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeliest.getLikeliest(position);
            }
        });

    }

    Likeliest likeliest;

    interface Likeliest {
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

    @Override
    public int getItemCount() {
        return hot_product_list_bean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_ordinary_right;
        private TextView tv_ordinary_right;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_ordinary_right = itemView.findViewById(R.id.iv_ordinary_right);
            tv_ordinary_right = itemView.findViewById(R.id.tv_ordinary_right);

        }
    }
}
