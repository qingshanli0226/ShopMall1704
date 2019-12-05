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
    //TODO Get请求数据单类型
    void onHttpGetRequest();
    //TODO post请求数据单类型
    void onHttpPostRequest();
    //TODO Get请求数据多类型
    void onHttpGetRequest(int requestCode);
    //TODO post请求数据多类型
    void onHttpPostRequest(int requestCode);
    //TODO 发起请求
    void getDate(Observable<ResponseBody> data);
    //TODO 发起请求
    void getDate(int requestCode,Observable<ResponseBody> data);

}
