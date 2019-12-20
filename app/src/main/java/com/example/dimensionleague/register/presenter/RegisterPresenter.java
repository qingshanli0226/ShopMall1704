package com.example.dimensionleague.register.presenter;

import com.example.dimensionleague.userbean.RegisterBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * author:李浩帆
 */
public class RegisterPresenter extends BasePresenter<RegisterBean> {

    private final Map<String,String> map;

    public RegisterPresenter(Map<String, String> map) {
        this.map = map;
    }

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
        return map;
    }
}
