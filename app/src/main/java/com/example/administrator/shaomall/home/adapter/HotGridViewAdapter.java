package com.example.administrator.shaomall.home.adapter;

import com.example.administrator.shaomall.home.HomeBean;
import com.example.administrator.shaomall.home.holder.HotViewHolder;
import com.shaomall.framework.base.BaseHolder;
import com.shaomall.framework.base.BaseListAdapter;

import java.util.List;

public class HotGridViewAdapter extends BaseListAdapter<HomeBean.ResultBean.HotInfoBean> {
    public HotGridViewAdapter(List<HomeBean.ResultBean.HotInfoBean> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder<HomeBean.ResultBean.HotInfoBean> geHolder() {
        return new HotViewHolder();
    }
}
