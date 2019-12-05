package com.example.shopmall.presenter;

import com.example.base.BasePresenter;
import com.example.shopmall.bean.HomepageBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class WelcomePresenter extends BasePresenter<HomepageBean> {

    @Override
    protected Type getBeanType() {
        return new TypeToken<HomepageBean>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        return "HOME_URL.json";
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
