package com.example.administrator.shaomall.mine;

import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;

public class LogOutPresenter extends BasePresenter<String> {
    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<String>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.LOGOUT_URL;
    }
}
