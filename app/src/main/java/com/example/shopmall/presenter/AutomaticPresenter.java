package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.framework.bean.LoginBean;
import com.example.framework.manager.UserManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class AutomaticPresenter extends BasePresenter {

    private String token;

    public AutomaticPresenter(String token) {
        this.token = token;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<LoginBean>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        return "autoLogin";
    }

    @Override
    protected HashMap<String, String> getHeader() {
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("token", token);
        return new HashMap<>();
    }

    @Override
    protected HashMap<String, String> getParam() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token",token);
        return hashMap;
    }
}
