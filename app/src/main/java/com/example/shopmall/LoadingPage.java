package com.example.shopmall;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LoadingPage extends LinearLayout {

    private static int LOADING_SUCCEED = 1;
    private static int LOADING_FAILURE = 0;
    private int current;
    private Context mContext;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    ImageView imageView;

    private void init() {
        if (current == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.succees_layout, this);
            imageView = view.findViewById(R.id.iv_succeed);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
            if (!animationDrawable.isRunning()) {
                animationDrawable.start();
            }
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.failure_layout, this);
            imageView = view.findViewById(R.id.iv_succeed);
        }
    }


}
