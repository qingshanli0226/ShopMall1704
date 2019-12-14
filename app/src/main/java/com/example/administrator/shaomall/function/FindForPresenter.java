package com.example.administrator.shaomall.function;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.shaomall.function.bean.FindForBean;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;

public class FindForPresenter extends BasePresenter<FindForBean> {
    private String path;

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<FindForBean>>(){}.getType();
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
