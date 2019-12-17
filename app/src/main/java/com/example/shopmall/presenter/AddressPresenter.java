package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.shopmall.bean.AddressBean;
import com.example.shopmall.bean.RegisterBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class AddressPresenter extends BasePresenter {

    private String phone;
    private String path;
    private String token;

    public AddressPresenter() {
    }

    public AddressPresenter(String path, String token) {
        this.path = path;
        this.token = token;
    }

    public AddressPresenter(String phone, String path, String token) {
        this.phone = phone;
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
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        return map;
    }
}
