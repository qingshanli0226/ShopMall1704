package com.example.dimensionleague.type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dimensionleague.R;
import com.example.common.TypeBean;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

public class TypeChildRecycleAdapter  extends BaseQuickAdapter<TypeBean.ResultBean.ChildBean, BaseViewHolder> {
    public TypeChildRecycleAdapter(int layoutResId, @Nullable List<TypeBean.ResultBean.ChildBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TypeBean.ResultBean.ChildBean item) {
        Glide.with(mContext).load(AppNetConfig.BASE_URl_IMAGE + item.getPic()).into((ImageView) helper.getView(R.id.type_right_iv_ordinary_right));
        helper.setText(R.id.type_right_tv_ordinary_right,item.getName());
        helper.addOnClickListener(R.id.type_right_iv_ordinary_right);
    }

}