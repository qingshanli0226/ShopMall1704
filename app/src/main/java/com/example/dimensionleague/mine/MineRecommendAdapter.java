package com.example.dimensionleague.mine;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.buy.activity.GoodsActiviy;
import com.example.common.HomeBean;
import com.example.common.IntentUtil;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.OnClickItemListener;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

public class MineRecommendAdapter extends BaseRecyclerAdapter<HomeBean.ResultBean.SeckillInfoBean.ListBean> {
//我的页面第三个RecycleView

    public MineRecommendAdapter(int layoutResId, @Nullable List<HomeBean.ResultBean.SeckillInfoBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    public void onBind(BaseViewHolder holder, int position) {
        holder.getTextView(R.id.mine_rcv_recommend_title,dateList.get(position).getName());
        holder.getTextView(R.id.mine_rcv_recommend_price,dateList.get(position).getCover_price());
        holder.getImageView(R.id.mine_rcv_recommend_img,AppNetConfig.BASE_URl_IMAGE+dateList.get(position).getFigure());
        setClickListener(new OnClickItemListener() {
            @Override
            public void onClickListener(int position) {
                Intent intent = new Intent(holder.itemView.getContext(), GoodsActiviy.class);
                intent.putExtra(IntentUtil.SHOW_GOOD,dateList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

}
