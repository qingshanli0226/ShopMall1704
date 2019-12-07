package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

public class RegisterPresenter extends BasePresenter {

    private String Config;
    private Type type;

    private HashMap<String,String> header;
    private HashMap<String,String> query;

    public RegisterPresenter(String config) {
        Config = config;
    }

    public RegisterPresenter(String config, Type type) {
        Config = config;
        this.type = type;
    }

    public RegisterPresenter(String config, Type type, HashMap<String, String> query) {
        Config = config;
        this.type = type;
        this.query = query;
    }

    @Override
    protected Type getBeanType() {
        return type;
    }

    @Override
    protected String getPath() {
        return Config;
    }

    @Override
    protected HashMap<String, String> getHeader() {
        return new HashMap<>();
    }

    @Override
    protected HashMap<String, String> getQuery() {
        return query;
    }
}
