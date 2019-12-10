package com.example.administrator.shaomall.type;

import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.util.List;

public class TypePresenter extends BasePresenter<TypeBean> {
    private String path;
    @Override
    protected Type getBeanType() {
//        return TypeBean.class;
        return new TypeToken<ResEntity<List<TypeBean>>>(){}.getType();
    }

    @Override
    protected boolean isList() {
        return true;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
