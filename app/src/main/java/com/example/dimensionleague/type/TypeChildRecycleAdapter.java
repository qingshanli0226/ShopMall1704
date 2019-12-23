package com.example.dimensionleague.type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dimensionleague.R;
import com.example.common.TypeBean;
import com.example.net.AppNetConfig;

import java.util.ArrayList;

public class TypeChildRecycleAdapter  extends RecyclerView.Adapter<TypeChildRecycleAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<TypeBean.ResultBean.ChildBean> childBeans;

    public TypeChildRecycleAdapter(Context mContext, ArrayList<TypeBean.ResultBean.ChildBean> childBeans) {
        this.mContext = mContext;
        this.childBeans = childBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.type_right_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(AppNetConfig.BASE_URl_IMAGE + childBeans.get(position).getPic()).into(holder.iv_ordinary_right);
        holder.tv_ordinary_right.setText(childBeans.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return childBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView iv_ordinary_right;
        final TextView tv_ordinary_right;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_ordinary_right = itemView.findViewById(R.id.type_right_iv_ordinary_right);
            tv_ordinary_right = itemView.findViewById(R.id.type_right_tv_ordinary_right);

        }
    }
}