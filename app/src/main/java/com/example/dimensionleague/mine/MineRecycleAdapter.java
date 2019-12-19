package com.example.dimensionleague.mine;

import com.example.common.HomeBean;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.net.AppNetConfig;
import java.util.List;

public class MineRecycleAdapter extends BaseRecyclerAdapter<HomeBean.ResultBean.ChannelInfoBean> {
    //我的页面第二个RecycleView

    public MineRecycleAdapter(int layoutId, List<HomeBean.ResultBean.ChannelInfoBean> dateList) {
        super(layoutId, dateList);
    }

    @Override
    public void onBind(BaseViewHolder holder, int position) {
        holder.getTextView(R.id.mine_rcv_h_name,dateList.get(position).getChannel_name());
        holder.getImageView(R.id.mine_rcv_h_img,AppNetConfig.BASE_URl_IMAGE+dateList.get(position).getImage());
    }
}