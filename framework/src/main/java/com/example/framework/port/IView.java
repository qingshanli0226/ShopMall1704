package com.example.framework.port;

/**
 * author:李浩帆
 */
public interface IView<T> {
    //TODO 显示加载页面
    void showLoading();
    //TODO 隐藏加载页面
    void hideLoading();
    //TODO 返回get请求数据
    void onHttpGetRequestDataSuccess(T data);
    //TODO 返回post请求数据
    void onHttpPostRequestDataSuccess(T data);
}
