package com.example.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.common.R;


@SuppressLint("AppCompatCustomView")
public class MyOKButton extends Button {

    public MyOKButton(Context context) {
        super(context);
        initView(context);
    }

    public MyOKButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyOKButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.setBackgroundColor(getResources().getColor(R.color.color_lightred));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(getResources().getColor(R.color.colorcheckedRed));
                break;
            case MotionEvent.ACTION_UP:
                this.setBackgroundColor(getResources().getColor(R.color.color_lightred));
                break;
        }
        return super.onTouchEvent(event);
    }
}
