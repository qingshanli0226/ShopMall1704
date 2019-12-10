package com.example.framework.base;

//回调接口
public interface IGetBaseView<T> {

    void onGetDataSucess(T data);

    //请求数据失败
    void onGetDataFailed(String ErrorMsg);
}
