package com.example.common;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

//自定义加载页
public class LoadingPage extends LinearLayout {

    public static int LOADING_SUCCEED = 1;
    public static int LOADING_FAILURE = 0;
    private int current;
    private Context mContext;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    //初始化
    private void init() {
        //如果是加载中
        if (current == LOADING_SUCCEED) {
            //获取布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.succees_layout, this);
            imageView = view.findViewById(R.id.iv_succeed);
            animationDrawable = (AnimationDrawable) imageView.getBackground();
            if (!animationDrawable.isRunning()) {
                animationDrawable.start();//开始加载动画
            }
        } else if (current == LOADING_FAILURE) {//如果是加载失败
            //获取布局
            View view = LayoutInflater.from(mContext).inflate(R.layout.failure_layout, this);
            imageView = view.findViewById(R.id.iv_failure);
        }
    }

    public void start(int current) {
        this.current = current;
        removeAllViews();//移除view
        init();
    }
    //加载成功时调用
    public void isSucceed() {
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        this.setVisibility(GONE);
    }


}
