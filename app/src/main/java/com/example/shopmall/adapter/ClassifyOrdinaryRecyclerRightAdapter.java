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
 * 常用分类
 */
public class ClassifyOrdinaryRecyclerRightAdapter extends RecyclerView.Adapter<ClassifyOrdinaryRecyclerRightAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ClassifyBean.ResultBean.ChildBean> child_bean;

    public ClassifyOrdinaryRecyclerRightAdapter(Context context, ArrayList<ClassifyBean.ResultBean.ChildBean> child_bean) {
        this.context = context;
        this.child_bean = child_bean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ordinary_right, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Constant.BASE_URL_IMAGE + child_bean.get(position).getPic()).into(holder.iv_ordinary_right);
        holder.tv_ordinary_right.setText(child_bean.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return child_bean.size();
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
