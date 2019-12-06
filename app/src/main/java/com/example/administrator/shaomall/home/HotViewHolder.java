package com.example.administrator.shaomall.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.ShaoHuaApplication;
import com.example.net.AppNetConfig;
import com.shaomall.framework.base.BaseHolder;

public class HotViewHolder extends BaseHolder<HomeBean.ResultBean.HotInfoBean> {
    private ImageView mIvHot;
    private TextView mTvName;
    private TextView mTvPrice;

    @Override
    protected View initView() {
        View rootView = View.inflate(ShaoHuaApplication.context, R.layout.item_hot_item, null);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void refreshData() {
        Glide.with(ShaoHuaApplication.context).load(AppNetConfig.BASE_URl_IMAGE+getDatas().getFigure()).into(mIvHot);
        mTvName.setText(getDatas().getName());
        mTvPrice.setText("￥"+getDatas().getCover_price());
    }

    private void initView(View view) {
        mIvHot = view.findViewById(R.id.iv_hot);
        mTvName = view.findViewById(R.id.tv_name);
        mTvPrice = view.findViewById(R.id.tv_price);
    }
}
