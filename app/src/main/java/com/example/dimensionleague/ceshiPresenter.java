package com.example.dimensionleague;

import com.example.common.SPUtil;
import com.example.common.SignUtil;
import com.example.dimensionleague.userbean.LoginBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ceshiPresenter extends BasePresenter<LoginBean> {

    @Override
    public Type getBeanType() {
        return LoginBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.LOGIN;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("name","li222");
        params.put("password","li222");
        return params;
    }
}
