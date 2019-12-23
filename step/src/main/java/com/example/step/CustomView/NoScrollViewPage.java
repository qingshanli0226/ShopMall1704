package com.example.step.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPage extends ViewPager {

    private boolean scrollable=false;
    public NoScrollViewPage(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!scrollable){
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!scrollable){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean isScrollable() {
        return scrollable;
    }
    public void setScrollable(boolean scrollable){
        this.scrollable=scrollable;

    }
}
