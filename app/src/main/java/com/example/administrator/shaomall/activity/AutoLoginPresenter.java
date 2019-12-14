package com.example.administrator.shaomall.activity;

import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.manager.UserInfoManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AutoLoginPresenter extends BasePresenter<Object> {
    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<LoginBean>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.AUTO_LOGIN_URL;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", UserInfoManager.getInstance().getToken());
        return map;
    }
}
