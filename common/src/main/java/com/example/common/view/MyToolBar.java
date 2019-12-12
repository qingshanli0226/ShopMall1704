package com.example.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.common.R;

public class MyToolBar extends RelativeLayout {
    public MyToolBar(Context context) {
        super(context);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.toolbar_layout,this);
        init(context);
    }

    private void init(Context context) {

    }

}
