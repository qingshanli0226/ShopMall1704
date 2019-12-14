package com.example.point.message;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.point.R;

import java.util.List;

public class MessageAdpter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    Context context;
    public MessageAdpter(int layoutResId, @Nullable List<MessageBean> data,Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.message_title,item.message_title).setText(R.id.message_message,item.message_message);
        ImageView view = helper.getView(R.id.message_img);
        Glide.with(context).load(item.message_img).into(view);
    }
}
