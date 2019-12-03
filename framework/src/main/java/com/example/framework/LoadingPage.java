package com.example.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

/**
 * author:李浩帆
 */
public class LoadingPage extends FrameLayout {

    //TODO 加载的状态
    private static final int STATE_LOADING = 1;
    //TODO 加载失败的状态
    private static final int STATE_ERROR = 2;
    //TODO 加载空的状态
    private static final int STATE_EMPTY = 3;
    //TODO 加载成功的状态
    private static final int STATE_SUCCEED =4;

    private View loadingView;// 加载的view
    private View errorView;// 错误的view
    private View emptyView;// 空的view
    private boolean isAddLoadPage = false;
    private boolean isAddErrorPage = false;
    private boolean isAddEmptyPage = false;
    private LayoutParams layoutParams;

    private int mState;// 默认的状态

    private int loadPage_empty;
    private int loadPage_error;
    private int loadPage_loading;

    private Context context;

    public LoadingPage(Context context, int loading, int error, int empty) {
        this(context, null, loading, error, empty);

    }

    public LoadingPage(Context context, AttributeSet attrs, int loading, int error, int empty) {
        this(context, attrs, 0, loading, error, empty);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int loading, int error, int empty) {
        super(context, attrs, defStyleAttr);
        loadPage_empty = empty;
        loadPage_error = error;
        loadPage_loading = loading;

        this.context = context;
        init();
    }

    public void setmState(int mState) {
        this.mState = mState;
    }


    private void init() {
        //TODO 初始化状态
        mState = STATE_LOADING;
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //TODO 初始化三个 状态的view 这个时候 三个状态的view叠加在一起了
        loadingView = createLoadingView();
        errorView = createErrorView();
        emptyView = createEmptyView();

    }

    private void showPage(){
        switch (mState){
            case STATE_LOADING:
                if (null != loadingView || isAddLoadPage == false) {
                    removeAllViews();
                    isAddEmptyPage = false;
                    isAddErrorPage = false;
                    addView(loadingView,layoutParams);
                    isAddLoadPage = true;

                }
                break;
            case STATE_ERROR:
                if (null != errorView || isAddErrorPage == false) {
                    removeAllViews();
                    isAddEmptyPage = false;
                    isAddLoadPage = false;
                    addView(errorView,layoutParams);
                    isAddErrorPage = true;
                }
                break;
            case STATE_EMPTY:
                if (null != emptyView || isAddEmptyPage== false) {
                    removeAllViews();
                    isAddLoadPage = false;
                    isAddErrorPage = false;
                    addView(emptyView,layoutParams);
                    isAddEmptyPage = true;

                }
                break;
            case STATE_SUCCEED:
                removeAllViews();
                break;
        }
    }

    //TODO 创建空页面
    private View createEmptyView() {
        View inflate = LayoutInflater.from(context).inflate(loadPage_empty, this);
        ImageView imageView = inflate.findViewById(R.id.image_empty);
//        Glide.with(context).load(R.mipmap)
        return inflate;
    }

    //TODO 创建错误页面
    private View createErrorView() {
        View inflate = LayoutInflater.from(context).inflate(loadPage_error, this);
        ImageView imageView = inflate.findViewById(R.id.image_empty);
//        Glide.with(context).load(R.mipmap)
        return inflate;
    }

    //TODO 创建加载页面
    private View createLoadingView() {
        View inflate = LayoutInflater.from(context).inflate(loadPage_loading, this);
        ImageView imageView = inflate.findViewById(R.id.image_empty);
//        Glide.with(context).load(R.mipmap)
        return inflate;
    }
}
