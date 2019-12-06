package com.example.administrator.shaomall.home;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import com.shaomall.framework.bean.LoginBean;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HomePresenter extends BasePresenter<LoginBean> {
    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<LoginBean>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.LOGIN_URL;
    }

    @Override
    protected boolean isList() {
        return false;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", "123");
        params.put("password", "123");

        return params;
    }
}
