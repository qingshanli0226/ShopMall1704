package com.example.framework.base;

public interface IBasePresenter<T> {


    void attachView(IBaseView<T> baseView);

    void detachView();

    void getGetData();

    void getPostJsonData();

    void getPostFormData();

    void register();

    void login();

}
