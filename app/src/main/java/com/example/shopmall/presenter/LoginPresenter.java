package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.framework.bean.LoginBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * 登录
 */
public class LoginPresenter extends BasePresenter<LoginBean> {

    private String name;
    private String pwd;

    public LoginPresenter() {
    }

    public LoginPresenter(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<LoginBean>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        return "login";
    }

    @Override
    protected HashMap<String, String> getHeader() {
        return new HashMap<>();
    }

    @Override
    protected HashMap<String, String> getParam() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("password", pwd);
        return map;
    }

}
