package com.example.dimensionleague.login.presenter;

import com.example.dimensionleague.userbean.LoginBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * author:李浩帆.
 */
public class LoginPresenter extends BasePresenter<LoginBean> {

    private final Map<String,String> map;

    public LoginPresenter(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public Type getBeanType() {
        return LoginBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.LOGIN;
    }

    @Override
    public Map<String, String> getParams() {
        return map;
    }
}
