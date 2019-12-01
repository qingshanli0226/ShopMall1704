package com.example.framework.base;

import com.example.framework.port.IPresenter;
import com.example.framework.port.IView;

public class BasePresenter<T> implements IPresenter<T> {

    private IView<T> iView;


    public void getDate(){

    }

    @Override
    public void attachView(IView iView) {
        this.iView = iView;
    }

    //TODO 销毁IView
    @Override
    public void detachView() {
        if(iView!=null){
            iView = null;
        }
    }
}
