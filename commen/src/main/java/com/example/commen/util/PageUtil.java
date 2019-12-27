package com.example.commen.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.example.commen.R;


public class PageUtil {
    private boolean isLoading = false;
    //TODO 找到加载页面的布局
    private View inflate;
    //TODO 设置布局为Match_Parent
    private RelativeLayout.LayoutParams params;
    //TODO 传回fragment里的RelativeLayout
    private RelativeLayout review;
    //TODO 上下文
    private Context context;

    //TODO get方法set方法
    public RelativeLayout getReview() {
        return review;
    }

    public void setReview(RelativeLayout review) {
        this.review = review;
    }

    //TODO 传回上下文
    public PageUtil(Context context) {
        this.context = context;
        init();
    }

    @SuppressLint("InflateParams")
    public void init() {
        //TODO 找到加载页面的布局
        inflate = LayoutInflater.from(context).inflate(R.layout.loadphoto, null);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView = inflate.findViewById(R.id.loadPhotoImageView);
        Glide.with(context).load(R.mipmap.tu1).into(imageView);
        //Picasso.with(context).load(R.mipmap.tu1).into(imageView);

    }

    public void showLoad() {
        if (!isLoading) {
            getReview().addView(inflate, params);
            isLoading = !isLoading;
        }

    }

    public void hideload() {
        if (isLoading) {
            getReview().removeView(inflate);
            isLoading = !isLoading;

        }
    }
}
