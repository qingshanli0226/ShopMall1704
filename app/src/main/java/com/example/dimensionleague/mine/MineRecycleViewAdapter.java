package com.example.dimensionleague.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

public class MineRecycleViewAdapter extends BaseQuickAdapter<MineBean, BaseViewHolder> {
//我的页面第一个RecycleView
    public MineRecycleViewAdapter(int layoutResId, @Nullable List<MineBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MineBean item) {
        helper.setText(R.id.mine_rcv_name,item.getTitle());
        ImageView img = helper.getView(R.id.mine_rcv_img);
        Glide.with(img.getContext()).load(item.getImg()).into(img);
    }

}