package com.example.shopmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class AiWebview extends WebView {
    public AiWebview(Context context) {
        super(context);
    }

    public AiWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AiWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
