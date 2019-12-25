package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.shopmall.bean.AutoLoginBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * 自动登录
 */
public class AutoLoginPresenter extends BasePresenter<AutoLoginBean> {

    private String token;

    public AutoLoginPresenter() {
    }

    public AutoLoginPresenter(String token) {
        this.token = token;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<AutoLoginBean>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        return "autoLogin";
    }

    @Override
    protected HashMap<String, String> getHeader() {
        return new HashMap<>();
    }

    @Override
    protected HashMap<String, String> getParam() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
