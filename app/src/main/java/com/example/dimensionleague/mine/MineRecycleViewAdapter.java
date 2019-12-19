package com.example.dimensionleague.mine;

import androidx.annotation.Nullable;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseRecyclerAdapter;
import java.util.List;

public class MineRecycleViewAdapter extends BaseRecyclerAdapter<MineBean>  {
//我的页面第一个RecycleView
    public MineRecycleViewAdapter(int layoutResId, @Nullable List<MineBean> data) {
        super(layoutResId, data);

    }
    @Override
    public void onBind(com.example.framework.base.BaseViewHolder holder, int position) {
        holder.getTextView(R.id.mine_rcv_name,dateList.get(position).getTitle());
        holder.getImageView(R.id.mine_rcv_img,dateList.get(position).getImg());

    }
}