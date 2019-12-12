package com.example.framework.port;

import com.example.common.code.ErrorCode;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

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
    //TODO 请求单数据成功
    void onRequestSuccess(T data);
    //TODO 请求多数据成功
    void onRequestSuccess(int requestCode, T data);
    //TODO 获取RelativeLayoutId
    int getRelativeLayout();
    //TODO 返回错误信息
    void onHttpRequestDataFailed(int requestCode, ErrorCode error);
}
