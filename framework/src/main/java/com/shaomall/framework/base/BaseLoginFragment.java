package com.shaomall.framework.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commen.ShopMailError;
import com.example.commen.bean.LoginBean;
import com.shaomall.framework.manager.UserInfoManager;

import java.util.List;

public abstract class BaseLoginFragment<T> extends BaseFragment<T> implements UserInfoManager.UserInfoStatusListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserInfoManager.getInstance().registerUserInfoStatusListener(this);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view, savedInstanceState);
    }

    protected abstract void initView(View view, Bundle savedInstanceState);

    @LayoutRes
    public abstract int getLayoutId();

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
    public void loadingPage(int code) {

    }


    @Override
    public void onUserStatus(boolean isLogin, LoginBean userInfo) {

    }
}
