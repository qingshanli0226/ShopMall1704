package com.example.shoppingcart.presenter;

import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;

public class ShoppingcartPresenter extends BasePresenter<String> {

    @Override
    protected Type getBeanType() {

        return new TypeToken<ResEntity<String>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.GET_SHORTCART_PRODUCTS_URL;
    }

//    @Override
//    public HashMap<String, String> getHeaderParams() {
//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put("token",UserInfoManager.getInstance().getToken());
//
//        return map;
//
//    }

    @Override
    protected boolean isList() {
        return false;

    }
}
