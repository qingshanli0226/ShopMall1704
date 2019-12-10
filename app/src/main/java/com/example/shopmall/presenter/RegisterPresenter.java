package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.shopmall.bean.RegisterBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class RegisterPresenter extends BasePresenter {
    private String name;
    private String pwd;

    public RegisterPresenter() {
    }

    public RegisterPresenter(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<RegisterBean>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        return "register";
    }

    @Override
    protected HashMap<String, String> getHeader() {
        return null;
    }

    @Override
    protected HashMap<String, String> getParam() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("password", pwd);
        return map;
    }
}
