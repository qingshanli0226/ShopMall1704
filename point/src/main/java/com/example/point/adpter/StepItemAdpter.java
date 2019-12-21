package com.example.point.adpter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.point.R;
import com.example.framework.bean.StepBean;

import java.util.List;

public class StepItemAdpter extends BaseQuickAdapter<StepBean, BaseViewHolder> {
    public StepItemAdpter(@Nullable List<StepBean> data) {
        super(R.layout.item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StepBean item) {
        helper.setText(R.id.wzy_currdate,"时间"+item.getCurr_date()).setText(R.id.wzy_step,item.getStep()+"步");
    }
}
