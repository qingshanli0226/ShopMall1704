package com.shaomall.framework.base;

import com.example.commen.util.ShopMailError;
import com.shaomall.framework.base.view.IBaseView;

import java.util.List;

public abstract class BaseMVPActivity<T> extends BaseActivity implements IBaseView<T> {
    //    private IBasePresenter<T> iBasePresenter;

    @Override
    protected void initData() {
    }

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
    }
}
