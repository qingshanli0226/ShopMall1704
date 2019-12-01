package com.example.framework.port;

import java.util.List;

public interface IView<T> {
    //TODO 获取布局Id
    int getLayoutId();
    //TODO 显示加载页面
    void showLoading();
    //TODO 隐藏加载页面
    void hideLoading();
    //TODO 返回对象数据
    void onHttpRequestDataSuccess(int requestCode, T data);
    //TODO 返回列表数据
    void onHttpRequestDataListSuccess(int requestCode, List<T> data);
}
