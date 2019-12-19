package com.example.point.adpter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.point.R;
import com.example.framework.bean.PointBean;

import java.util.List;

public class ConversionAdpter extends BaseQuickAdapter<PointBean, BaseViewHolder> {
    public ConversionAdpter(int layoutResId, @Nullable List<PointBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PointBean item) {
        helper.setText(R.id.conversionitem_tv,"在"+item.getCurr_date()+"消耗了"+item.getPoint()+"积分,兑换了"+item.getBuy_title());
    }
}
