package com.example.framework.port;

/**
 * author:李浩帆
 */
public interface IView<T> {
    //TODO 显示加载页面
    void showLoading();
    //TODO 隐藏加载页面
    void hideLoading();
    //TODO 显示错误页面
    void showError();
    //TODO 隐藏错误页面
    void hideError();
    //TODO 显示无网络页面
    void showEmpty();
    //TODO 隐藏无网络页面
    void hideEmpty();
    //TODO 返回get请求数据
    void onHttpGetRequestDataSuccess(int requestCode,T data);
    //TODO 返回post请求数据
    void onHttpPostRequestDataSuccess(int requestCode,T data);
    //TODO 获取RelativeLayoutId
    int getRelativeLayout();
}
