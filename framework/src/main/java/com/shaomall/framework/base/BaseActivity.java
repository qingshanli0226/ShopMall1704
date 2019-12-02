package com.shaomall.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.commen.ShopMailError;
import com.shaomall.framework.base.view.IBaseView;
import com.shaomall.framework.manager.ActivityInstanceManager;

import java.util.List;

public abstract class BaseActivity<T> extends AppCompatActivity implements IBaseView<T> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();

        //activity 管理类
        ActivityInstanceManager.addActivity(this);




    }

    protected abstract void initView();

    public abstract View getLayoutId();







    @Override
    public void onRequestHttpDataSuccess(int requestCode, T data) {

    }

    @Override
    public void onRequestHttpDataListSuccess(int requestCode, List<T> data) {

    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {

    }

    @Override
    public void loadingPage(int code) {

    }



}
