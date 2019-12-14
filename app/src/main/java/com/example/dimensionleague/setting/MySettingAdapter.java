package com.example.dimensionleague.setting;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseRecyclerAdapter;

import java.util.List;

public class MySettingAdapter extends BaseQuickAdapter<SettingBean, BaseViewHolder> {

    public MySettingAdapter(int layoutResId, @Nullable List<SettingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingBean item) {
        helper.setText(R.id.setting_item_title,item.getTitle()).addOnClickListener(R.id.setting_item_title);
        helper.setText(R.id.setting_item_name,item.getMassage());

    }
}