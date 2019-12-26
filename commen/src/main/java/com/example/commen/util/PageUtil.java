package com.example.commen.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.example.commen.R;


public class PageUtil {
    boolean isloading=false;
    //TODO 找到加载页面的布局
    View inflate;
    //TODO 设置布局为Match_Parent
    RelativeLayout.LayoutParams params;
    //TODO 传回fragment里的Relativelayout
    private RelativeLayout review;
    //TODO 上下文
    public Context context;
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
    public void init(){
        //TODO 找到加载页面的布局
        inflate= LayoutInflater.from(context).inflate(R.layout.loadphoto, null);
        params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView=inflate.findViewById(R.id.loadPhotoImageView);
        Glide.with(context).load(R.mipmap.tu1).into(imageView);
        //Picasso.with(context).load(R.mipmap.tu1).into(imageView);

    }
    public void showLoad(){
        if (!isloading){
            getReview().addView(inflate,params);
            isloading=!isloading;
        }

    }
    public void hideload(){
        if (isloading){
            getReview().removeView(inflate);
            isloading=!isloading;

        }
    }
}
