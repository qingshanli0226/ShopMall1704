package com.example.shoppingcart.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import java.lang.reflect.Type;
public class UpDateShoppingcartPresenter extends BasePresenter<Object> {

    private JSONObject jsonParam;

    public void setJsonParam(JSONObject jsonParam) {
        this.jsonParam = jsonParam;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<String>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.UPDATE_PRODUCT_NUM_URL;
    }

    @Override
    protected JSONObject getJsonParam() {

        return jsonParam;
    }


}
