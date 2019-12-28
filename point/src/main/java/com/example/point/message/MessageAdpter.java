package com.example.point.message;


import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.framework.bean.MessageBean;
import com.example.point.R;


import java.util.List;

public class MessageAdpter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {

    public MessageAdpter(int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.message_title, item.getMessage_message())
                .setText(R.id.message_message, item.getMessage_title())
                .setText(R.id.message_date, item.getMessage_date());
        Glide.with(helper.itemView.getContext())
                .load(item.getMessage_img())
                .into((ImageView) helper.getView(R.id.message_img));
    }
}
