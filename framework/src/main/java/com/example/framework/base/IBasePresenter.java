package com.example.framework.base;

public interface IBasePresenter<T> {


    void attachView(IBaseView<T> baseView);

    void detachView();

    void getGetData();

    void getPostJsonData();

    void getPostFormData();

    void register(String user, String pwd);

    void login(String user, String pwd);

}
