package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.shopmall.bean.AddressBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * 收货地址详细地址
 */
public class LocationPresenter extends BasePresenter {

    private String address;
    private String path;
    private String token;

    public LocationPresenter() {
    }

    public LocationPresenter(String path, String token) {
        this.path = path;
        this.token = token;
    }

    public LocationPresenter(String address, String path, String token) {
        this.address = address;
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
        hashMap.put("token", token);
        return hashMap;
    }

    @Override
    protected HashMap<String, String> getParam() {
        HashMap<String, String> map = new HashMap<>();
        map.put("address", address);
        return map;
    }
}
