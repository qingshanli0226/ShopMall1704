package com.example.dimensionleague;

import com.example.dimensionleague.userbean.RegisterBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter extends BasePresenter<RegisterBean> {

    @Override
    public Type getBeanType() {
        return RegisterBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.REGISTER;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name","li222");
        hashMap.put("password","li222");
        return hashMap;
    }
}
