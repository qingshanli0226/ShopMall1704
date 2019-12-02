package com.example.framework.port;

import android.view.View;

public interface IPresenter<T> {
    //TODO 注入一个IView
    void attachView(IView<T> view);
    //TODO 结束时销毁
    void detachView();
}
