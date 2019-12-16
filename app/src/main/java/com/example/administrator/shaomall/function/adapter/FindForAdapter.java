package com.example.administrator.shaomall.function.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.shaomall.R;
import com.shaomall.framework.bean.FindForBean;
import com.shaomall.framework.base.IBaseRecyclerAdapter;

import java.util.List;

public class FindForAdapter extends IBaseRecyclerAdapter<FindForBean> {
    public FindForAdapter(@Nullable List<FindForBean> data) {
        super(R.layout.function_adapter_findfor, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindForBean item) {
        helper.setText(R.id.tv_function_title, item.getBody());     //标题
        helper.setText(R.id.tv_function_price, item.getTotalPrice()); //价格
        helper.setText(R.id.tv_function_status, item.getStatus().toString());      //付款状态
        helper.setText(R.id.tv_function_reserveOne, item.getReserveOne().toString());
    }
}
