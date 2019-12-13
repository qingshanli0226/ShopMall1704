package com.example.shoppingcart.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;

public class RemoveOneProductPresenter extends BasePresenter<Object> {


    public JSONObject json;

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject object) {
        this.json = object;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<String>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.REMOVE_ONE_PRODUCT;

    }

    @Override
    protected JSONObject getJsonParam() {
        return getJson();
    }
}
