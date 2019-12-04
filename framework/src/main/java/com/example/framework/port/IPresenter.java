package com.example.framework.port;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * author:李浩帆
 */
public interface IPresenter<T> {
    //TODO 注入一个IView
    void attachView(IView<T> view);
    //TODO 结束时销毁
    void detachView();
    //TODO Get请求数据
    void onHttpGetRequest(int requestCode);
    //TODO post请求数据
    void onHttpPostRequest(int requestCode);
    //TODO post JSON
    void onHttpPostJsonRequest(int requestCode);

    void onHttpRequest(int requestCode, Observable<ResponseBody> observable);
}
