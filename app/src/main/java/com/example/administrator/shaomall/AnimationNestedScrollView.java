package com.example.administrator.shaomall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

public class AnimationNestedScrollView extends NestedScrollView {
    private OnAnimationScrollChangeListener listener;

    public AnimationNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public AnimationNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnAnimationScrollListener(OnAnimationScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface OnAnimationScrollChangeListener {
        void onScrollChanged(float dy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScrollChanged(getScrollY() * 0.65f);//x0.65 使位移效果更加平滑 解决手指按住停留时抖动问题
        }
    }
}
