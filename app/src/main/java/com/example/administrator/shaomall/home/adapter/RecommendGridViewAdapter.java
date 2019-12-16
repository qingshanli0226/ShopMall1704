package com.example.administrator.shaomall.home.adapter;

import com.shaomall.framework.bean.HomeBean;
import com.example.administrator.shaomall.home.holder.RecommendViewHolder;
import com.shaomall.framework.base.BaseHolder;
import com.shaomall.framework.base.BaseListAdapter;

import java.util.List;

public class RecommendGridViewAdapter extends BaseListAdapter<HomeBean.ResultBean.RecommendInfoBean> {
    public RecommendGridViewAdapter(List<HomeBean.ResultBean.RecommendInfoBean> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder<HomeBean.ResultBean.RecommendInfoBean> geHolder() {
        return new RecommendViewHolder();
    }
}
