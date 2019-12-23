package com.example.shoppingcart.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import com.shaomall.framework.bean.ShoppingCartBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CheckInventoryPresenter extends BasePresenter<Object> {
    private final ArrayList<ShoppingCartBean> list;

    public CheckInventoryPresenter(ArrayList<ShoppingCartBean> data) {
        this.list = data;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<String>>() {
        }.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECK_INVENTORY_URL;
    }

    @Override
    protected Object getJsonArrayParam() {
        JSONObject[] objects = new JSONObject[list.size()];
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId", list.get(i).getProductId());
            jsonObject.put("productNum", list.get(i).getProductNum());
            jsonObject.put("productName", list.get(i).getProductName());
            jsonObject.put("url", list.get(i).getUrl());
            objects[i] = jsonObject;
        }

        return objects;
    }
}
