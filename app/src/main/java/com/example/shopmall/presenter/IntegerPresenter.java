package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

public class IntegerPresenter extends BasePresenter {

    private String Config;
    private Type type;


    public IntegerPresenter(String config) {
        Config = config;
    }

    public IntegerPresenter(String config, Type type) {
        Config = config;
        this.type = type;
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

    protected HashMap<String, String> getQuery() {
        return new HashMap<>();
    }
    protected HashMap<String, String> getParam() {
        return new HashMap<>();
    }

}
