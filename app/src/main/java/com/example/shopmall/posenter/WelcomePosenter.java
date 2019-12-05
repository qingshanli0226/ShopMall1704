package com.example.shopmall.posenter;

import com.example.base.BasePresenter;
import com.example.net.Constant;
import com.example.shopmall.bean.HomepageBean;

import java.lang.reflect.Type;
import java.util.HashMap;

public class WelcomePosenter extends BasePresenter {

    private String Config;
    private Type type;

    public WelcomePosenter(String config) {
        Config = config;
    }

    public WelcomePosenter(String config, Type type) {
        Config = config;
        this.type = type;
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
        return new HashMap<>();
    }
}
