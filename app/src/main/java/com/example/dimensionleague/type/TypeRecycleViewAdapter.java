package com.example.dimensionleague.type;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.example.buy.activity.GoodsActiviy;
import com.example.common.IntentUtil;
import com.example.dimensionleague.R;
import com.example.common.TypeBean;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.OnClickItemListener;
import com.example.net.AppNetConfig;

import java.util.List;

public class TypeRecycleViewAdapter extends BaseRecyclerAdapter<TypeBean.ResultBean.HotProductListBean> {


    public TypeRecycleViewAdapter(int layoutId, List<TypeBean.ResultBean.HotProductListBean> dateList) {
        super(layoutId, dateList);
    }

    @Override
    public void onBind(BaseViewHolder holder, int position) {
        holder.getTextView(R.id.type_right_tv_ordinary_right, "￥" + dateList.get(position).getCover_price()).setTextColor(Color.RED);
        holder.getImageView(R.id.type_right_iv_ordinary_right, AppNetConfig.BASE_URl_IMAGE + dateList.get(position).getFigure());
        setClickListener(new OnClickItemListener() {
            @Override
            public void onClickListener(int position) {
                linkedlist.getLinkedlist(position);

                Intent intent = new Intent(holder.itemView.getContext(), GoodsActiviy.class);
                intent.putExtra(IntentUtil.SHOW_GOOD, dateList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    Linkedlist linkedlist;

    interface Linkedlist {
        public void getLinkedlist(int position);
    }

    public void setLinkedlist(Linkedlist linkedlist) {
        this.linkedlist = linkedlist;
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

}
