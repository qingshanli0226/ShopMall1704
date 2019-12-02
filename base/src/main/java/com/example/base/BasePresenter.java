package com.example.base;

//网络下载
public class BasePresenter<T> implements IBasePresenter<T> {

    IBaseView<T> baseView;

    //绑定
    @Override
    public void attachView(IBaseView<T> baseView) {
        if (baseView == null) {
            this.baseView = baseView;
        }
    }
    //解绑
    @Override
    public void detachView() {
        this.baseView = null;
    }
}
