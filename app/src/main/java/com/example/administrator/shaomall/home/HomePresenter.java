package com.example.administrator.shaomall.home;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.util.List;

public class HomePresenter extends BasePresenter<HomeBean.ResultBean> {
    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<HomeBean.ResultBean>>(){}.getType();
//        return HomeBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.HOME_URL;
    }

    @Override
    protected boolean isList() {
        return false;
    }
}
