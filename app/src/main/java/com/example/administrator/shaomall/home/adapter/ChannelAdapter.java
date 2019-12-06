package com.example.administrator.shaomall.home.adapter;

import com.example.administrator.shaomall.home.ChannelViewHolder;
import com.example.administrator.shaomall.home.HomeBean;
import com.shaomall.framework.base.BaseHolder;
import com.shaomall.framework.base.BaseListAdapter;

import java.util.List;

public class ChannelAdapter extends BaseListAdapter<HomeBean.ResultBean.ChannelInfoBean> {
    public ChannelAdapter(List<HomeBean.ResultBean.ChannelInfoBean> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder<HomeBean.ResultBean.ChannelInfoBean> geHolder() {
        return new ChannelViewHolder();
    }


}
