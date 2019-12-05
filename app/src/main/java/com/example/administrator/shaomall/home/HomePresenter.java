package com.example.administrator.shaomall.home;

import android.app.Application;

import com.example.net.AppNetConfig;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;

public class HomePresenter extends BasePresenter<HomeBean> {
    @Override
    protected Type getBeanType() {
        return HomeBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.HOME_URL;
    }
}
