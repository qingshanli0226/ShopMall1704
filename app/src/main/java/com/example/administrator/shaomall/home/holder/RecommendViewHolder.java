package com.example.administrator.shaomall.home.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.app.ShaoHuaApplication;
import com.example.administrator.shaomall.home.HomeBean;
import com.example.net.AppNetConfig;
import com.shaomall.framework.base.BaseHolder;

public class RecommendViewHolder extends BaseHolder<HomeBean.ResultBean.RecommendInfoBean> {
    private ImageView mIvRecommend;
    private TextView mTvName;
    private TextView mTvPrice;

    @Override
    protected View initView() {
        View rootView = View.inflate(ShaoHuaApplication.context, R.layout.item_recommend_item, null);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void refreshData() {
        Glide.with(ShaoHuaApplication.context).load(AppNetConfig.BASE_URl_IMAGE+getDatas().getFigure()).into(mIvRecommend);
        mTvName.setText(getDatas().getName());
        mTvPrice.setText("￥"+getDatas().getCover_price());
    }

    private void initView(View view) {
        mIvRecommend = view.findViewById(R.id.iv_recommend);
        mTvName = view.findViewById(R.id.hot_item_name_tv);
        mTvPrice = view.findViewById(R.id.tv_price);
    }
}
