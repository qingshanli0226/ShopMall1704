package com.example.dimensionleague.type;

import com.example.common.TypeBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;

public class TypePresenter extends BasePresenter<TypeBean> {
    private String s;

    public void setURL(String s) {
        this.s = s;
    }

    @Override
    public Type getBeanType() {
        return TypeBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.BASE_URL_JSON+getURL();
    }

    public String getURL() {
        return s;
    }
}
