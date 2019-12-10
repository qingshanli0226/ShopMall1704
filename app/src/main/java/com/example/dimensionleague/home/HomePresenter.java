package com.example.dimensionleague.home;

import com.example.common.HomeBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;

public class HomePresenter extends BasePresenter<HomeBean> {
    @Override
    public Type getBeanType() {
        return HomeBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.BASE_URL_JSON+AppNetConfig.HOME_URL;
    }
}
