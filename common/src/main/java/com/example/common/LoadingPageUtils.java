package com.example.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * author:李浩帆
 */
public class LoadingPageUtils {
    //TODO 布局大小
    private RelativeLayout.LayoutParams params;
    //TODO 需要用的ViewGroup
    private RelativeLayout relativeLayout;
    //TODO 加载页面
    private View loadingView;
    //TODO 错误页面
    private View errorView;
    //TODO 空加载页面
    private View emptyView;
    //TODO 是否展示页面
    private boolean isLoading = false;
    private boolean isError = false;
    private boolean isEmpty = false;
    //TODO
    private LayoutInflater inflater;
    private RequestManager requestManager;
    public LoadingPageUtils(Context context, RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
        inflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
        init();
    }

    private void init() {
        //TODO 初始化加载页面
        loadingView = inflater.inflate(R.layout.page_loading,null);
        //TODO 初始化错误页面
        errorView = inflater.inflate(R.layout.page_error,null);
        //TODO 初始化空页面
        emptyView = inflater.inflate(R.layout.page_empty,null);
        //TODO 尺寸
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        //TODO 初始化各页面中的图片
        ImageView loading_image = loadingView.findViewById(R.id.loading_image);
        ImageView error_image = errorView.findViewById(R.id.error_image);
        ImageView empty_image = emptyView.findViewById(R.id.empty_image);
        //TODO 加载图片
        requestManager.load(R.drawable.longding_page).into(loading_image);
        requestManager.load(R.drawable.error_image).into(error_image);
        requestManager.load(R.drawable.empty_image).into(empty_image);
    }

    //TODO 显示加载页面
    public void showLoading(){
        if(!isLoading && relativeLayout !=null && loadingView!=null){
            relativeLayout.addView(loadingView,params);
            isLoading = true;
        }
    }

    //TODO 隐藏加载页面
    public void hideLoading(){
        if(isLoading && relativeLayout !=null && loadingView!=null){
            relativeLayout.removeView(loadingView);
            isLoading = false;
        }
    }

    //TODO 显示错误页面
    public void showError(){
        if(!isError && relativeLayout !=null && errorView!=null){
            relativeLayout.addView(errorView,params);
            isError = true;
        }
    }

    //TODO 隐藏错误页面
    public void hideError(){
        if(isError && relativeLayout !=null && errorView!=null){
            relativeLayout.removeView(errorView);
            isError = false;
        }
    }

    //TODO 显示空页面
    public void showEmpty(){
        if(!isEmpty && relativeLayout !=null && emptyView!=null){
            relativeLayout.addView(emptyView,params);
            isEmpty = true;
        }
    }

    //TODO 隐藏空页面
    public void hideEmpty(){
        if(isEmpty && relativeLayout !=null && emptyView!=null){
            relativeLayout.removeView(emptyView);
            isEmpty = false;
        }
    }


}
