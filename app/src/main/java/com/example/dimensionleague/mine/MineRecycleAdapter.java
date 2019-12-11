package com.example.dimensionleague.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.HomeBean;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseViewHolder;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

public class MineRecycleAdapter extends BaseQuickAdapter<HomeBean.ResultBean.ChannelInfoBean, com.chad.library.adapter.base.BaseViewHolder> {
    //我的页面第二个RecycleView
    public MineRecycleAdapter(int layoutResId, @Nullable List<HomeBean.ResultBean.ChannelInfoBean> data) {
        super(layoutResId,data);
    }
    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, HomeBean.ResultBean.ChannelInfoBean item) {
        helper.setText(R.id.mine_rcv_h_name,item.getChannel_name());
        ImageView img = helper.getView(R.id.mine_rcv_h_img);
        Glide.with(img.getContext()).load(AppNetConfig.BASE_URl_IMAGE+item.getImage()).into(img);
    }
}