package com.example.administrator.shaomall.function;

import com.shaomall.framework.bean.FindForBean;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.util.List;

public class FindForPresenter extends BasePresenter<FindForBean> {
    private String path;

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<List<FindForBean>>>(){}.getType();
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
