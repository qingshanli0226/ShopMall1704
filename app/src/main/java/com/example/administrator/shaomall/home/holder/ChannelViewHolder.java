package com.example.administrator.shaomall.home.holder;

import android.os.HandlerThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.app.ShaoHuaApplication;
import com.example.administrator.shaomall.home.HomeBean;
import com.example.net.AppNetConfig;
import com.shaomall.framework.base.BaseHolder;

public class ChannelViewHolder extends BaseHolder<HomeBean.ResultBean.ChannelInfoBean> {
    private ImageView mChannelItemIv;
    private TextView mChannelItemTv;

    @Override
    protected View initView() {
        View rootView = View.inflate(ShaoHuaApplication.context, R.layout.item_channel_item, null);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void refreshData() {
        HomeBean.ResultBean.ChannelInfoBean datas = this.getDatas();
        Glide.with(ShaoHuaApplication.context).load(AppNetConfig.BASE_URl_IMAGE+datas.getImage()).into(mChannelItemIv);
        mChannelItemTv.setText(datas.getChannel_name());
//        Thread.interrupted();
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//        thread.isInterrupted();


    }

    private void initView(View view) {
        mChannelItemIv = view.findViewById(R.id.channel_item_iv);
        mChannelItemTv = view.findViewById(R.id.channel_item_tv);
    }
}
