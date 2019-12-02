package com.example.base;

//回调接口
public interface IBaseView<T> {

    void onGetDataSucess(T data);

    void onPostDataSucess(T data);

    void onLoadingPage();

    void onStopLoadingPage();

}
