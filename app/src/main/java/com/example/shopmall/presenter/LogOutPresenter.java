package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.shopmall.bean.AddressBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * 退出登录
 */
public class LogOutPresenter extends BasePresenter {

    private String path;
    private String token;

    public LogOutPresenter() {
    }

    public LogOutPresenter(String path, String token) {
        this.path = path;
        this.token = token;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<AddressBean>() {}.getType();
    }

    @Override
    protected String getPath() {
        return path;
    }

    @Override
    protected HashMap<String, String> getHeader() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token",token);
        return hashMap;
    }

    @Override
    protected HashMap<String, String> getParam() {
        //        map.put("token", token);
        return new HashMap<>();
    }
}
