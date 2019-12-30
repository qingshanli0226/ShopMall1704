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
    private Context mContext;

    private PageUtil() {
    }

    public PageUtil(Context context) {
        this.mContext = context;
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        //加载页面
        inflate = LayoutInflater.from(mContext).inflate(R.layout.loadphoto, null);
        ImageView imageView = inflate.findViewById(R.id.loadPhotoImageView);
        Glide.with(mContext).load(R.mipmap.tu1).into(imageView);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }


    //TODO get方法set方法
    private RelativeLayout getReview() {
        return review;
    }

    public PageUtil setReview(RelativeLayout review) {
        this.review = review;
        return this;
    }

    public void showLoad() {
        if (!isLoading) {
            getReview().addView(inflate, params);
            isLoading = !isLoading;
        }
    }

    public void hideLoad() {
        if (isLoading) {
            getReview().removeView(inflate);
            isLoading = !isLoading;
        }
    }
}
