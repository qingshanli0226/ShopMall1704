package com.shaomall.framework.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.example.commen.util.ShopMailError;
import com.shaomall.framework.base.view.IBaseView;

import java.util.List;

public abstract class BaseMVPActivity<T> extends BaseActivity implements IBaseView<T> {
//    private IBasePresenter<T> iBasePresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//    protected abstract void initData(IBasePresenter<T> iBasePresenter);

//    protected abstract IBasePresenter<T> setBasePresenter();

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
    protected void onDestroy() {
        super.onDestroy();
//        iBasePresenter.detachView();
    }
}
