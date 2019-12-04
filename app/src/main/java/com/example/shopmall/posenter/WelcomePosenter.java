package com.example.shopmall.posenter;

import com.example.base.BasePresenter;
import com.example.net.Constant;
import com.example.shopmall.bean.HomepageBean;

import java.util.HashMap;

public class WelcomePosenter extends BasePresenter<HomepageBean> {

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
