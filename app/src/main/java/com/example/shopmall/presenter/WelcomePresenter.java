package com.example.shopmall.presenter;

import com.example.base.BasePresenter;
import com.example.shopmall.bean.HomepageBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

<<<<<<< HEAD:app/src/main/java/com/example/shopmall/posenter/WelcomePosenter.java
public class WelcomePosenter extends BasePresenter {

    private String Config;
    private Type type;

    public WelcomePosenter(String config) {
        Config = config;
    }

    public WelcomePosenter(String config, Type type) {
        Config = config;
        this.type = type;
=======
public class WelcomePresenter extends BasePresenter<HomepageBean> {

    @Override
    protected Type getBeanType() {
        return new TypeToken<HomepageBean>() {
        }.getType();
>>>>>>> one:app/src/main/java/com/example/shopmall/presenter/WelcomePresenter.java
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
