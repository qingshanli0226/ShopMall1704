package com.example.point.adpter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.point.R;
import com.example.point.bean.PresenBean;

import java.util.List;

public class PreAdpter  extends BaseQuickAdapter<PresenBean, BaseViewHolder> {
    Context context;
    public PreAdpter( @Nullable List<PresenBean> data, Context context) {
        super(R.layout.present_item, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PresenBean item) {
        helper.setText(R.id.present_tv,item.getPre_tv()).addOnClickListener(R.id.present_btn);
        ImageView view = helper.getView(R.id.present_img);
        Glide.with(context).load(item.getPre_img()).into(view);
    }
}