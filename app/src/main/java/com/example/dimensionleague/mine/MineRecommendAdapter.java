package com.example.dimensionleague.mine;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.HomeBean;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseViewHolder;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

public class MineRecommendAdapter  extends BaseQuickAdapter<HomeBean.ResultBean.SeckillInfoBean.ListBean, com.chad.library.adapter.base.BaseViewHolder> {
//我的页面第三个RecycleView

    public MineRecommendAdapter(int layoutResId, @Nullable List<HomeBean.ResultBean.SeckillInfoBean.ListBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, HomeBean.ResultBean.SeckillInfoBean.ListBean item) {
        helper.setText(R.id.mine_rcv_recommend_title,item.getName());
        helper.setText(R.id.mine_rcv_recommend_price,item.getCover_price());
        ImageView img = helper.getView(R.id.mine_rcv_recommend_img);
        Glide.with(img.getContext()).load(AppNetConfig.BASE_URl_IMAGE+item.getFigure()).into(img);
    }
}
