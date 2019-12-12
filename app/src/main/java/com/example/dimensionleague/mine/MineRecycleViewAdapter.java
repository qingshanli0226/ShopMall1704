package com.example.dimensionleague.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.buy.activity.OrderActivity;
import com.example.common.HomeBean;
import com.example.common.IntentUtil;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.net.AppNetConfig;
import com.example.point.activity.StepActivity;

import java.util.ArrayList;
import java.util.List;

public class MineRecycleViewAdapter extends BaseRecyclerAdapter<MineBean>  {
//我的页面第一个RecycleView
    public MineRecycleViewAdapter(int layoutResId, @Nullable List<MineBean> data) {
        super(layoutResId, data);

    }
    @Override
    public void onBind(com.example.framework.base.BaseViewHolder holder, int position) {
        holder.getTextView(R.id.mine_rcv_name,dateList.get(position).getTitle());
        holder.getImageView(R.id.mine_rcv_img,dateList.get(position).getImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onClickItem(position);
            }
        });
    }
    interface OnClickItemListener {
        void onClickItem(int position);
    };
    private OnClickItemListener itemListener;
    public void setOnClickItemListener(OnClickItemListener itemListener){
        this.itemListener=itemListener;
    }

}