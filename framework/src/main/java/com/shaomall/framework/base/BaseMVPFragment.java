package com.shaomall.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.commen.util.ShopMailError;
import com.shaomall.framework.base.view.IBaseView;

import java.util.List;

public abstract class BaseMVPFragment<T> extends BaseFragment implements IBaseView<T> {
//    private IBasePresenter<T> iBasePresenter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        //获取 IBasePresenter
//        if (iBasePresenter == null) {
//            iBasePresenter = setBasePresenter();
//        }
//        //进行绑定
//        if (iBasePresenter != null) {
//            iBasePresenter.attachView(this);
//        }
//        initData(iBasePresenter);
    }

    @Override
    protected void initData() {}


//    protected abstract IBasePresenter<T> setBasePresenter();

//    protected abstract void initData(IBasePresenter<T> iBasePresenter);


    @Override
    public void onRequestHttpDataSuccess(String message, T data) {

    }

    @Override
    public void onRequestHttpDataListSuccess(String message, List<T> data) {

    }

    @Override
    public void onRequestHttpDataFailed(ShopMailError error) {

    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, T data) {

    }

    @Override
    public void onRequestHttpDataListSuccess(int requestCode, String message, List<T> data) {

    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {

    }

    @Override
    public void loadingPage(int requestCode, int code) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        iBasePresenter.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
