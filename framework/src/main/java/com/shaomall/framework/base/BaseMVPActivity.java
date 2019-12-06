package com.shaomall.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.commen.ShopMailError;
import com.shaomall.framework.base.presenter.IBasePresenter;
import com.shaomall.framework.base.view.IBaseView;

import java.util.List;

public abstract class BaseMVPActivity<T> extends BaseActivity implements IBaseView<T> {
//    private IBasePresenter<T> iBasePresenter;

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
    public void onRequestHttpDataSuccess(int requestCode, String message, T data) {

    }

    @Override
    public void onRequestHttpDataListSuccess(int requestCode, String message, List<T> data) {

    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {

    }

    @Override
    public void loadingPage(int codee) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        iBasePresenter.detachView();
    }
}
