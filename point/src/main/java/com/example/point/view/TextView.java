package com.example.point.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class TextView extends android.widget.TextView {
    public TextView(Context context) {
        super(context,null);
    }

    public TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(-20, getMeasuredWidth(), getMeasuredHeight());
        super.onDraw(canvas);
    }
}
