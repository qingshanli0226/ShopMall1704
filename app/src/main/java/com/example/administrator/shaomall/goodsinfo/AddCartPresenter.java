package com.example.administrator.shaomall.goodsinfo;

import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;

public class AddCartPresenter extends BasePresenter<String> {
    private JSONObject jsonObject;
    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<String>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.ADD_ONE_PRODUCT_URL;
    }

    @Override
    protected JSONObject getJsonParam() {

        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject){
        this.jsonObject= jsonObject;
    }
}
