package com.shaomall.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.commen.util.ShopMailError;
import com.shaomall.framework.manager.UserInfoManager;

import java.util.List;

public abstract class BaseLoginFragment<T> extends BaseMVPFragment<T> implements UserInfoManager.UserInfoStatusListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserInfoManager.getInstance().registerUserInfoStatusListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

    //    @Override
    //    public void onUserStatus(boolean isLogin, LoginBean userInfo) {
    //ei


    //    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        UserInfoManager.getInstance().unRegisterUserInfoStatusListener(this);
    }
}
