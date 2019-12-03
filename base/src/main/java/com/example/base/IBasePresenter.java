package com.example.base;

public interface IBasePresenter<T> {


    void attachView(IBaseView<T> baseView);

    void detachView();

    void getData(T data);

}
