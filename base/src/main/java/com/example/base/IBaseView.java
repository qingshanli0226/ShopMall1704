package com.example.base;

//回调接口
public interface IBaseView<T> {

    void onGetDataSucess(T data);

    void onPostDataSucess(T data);

    //请求数据失败
    void onGetDataFailed(String ErrorMsg);

    void onLoadingPage();

    void onStopLoadingPage();

}
