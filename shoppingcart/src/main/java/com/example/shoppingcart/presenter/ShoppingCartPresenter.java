package com.example.shoppingcart.presenter;

import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.shaomall.framework.bean.ShoppingCartBean;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.util.List;

public class ShoppingCartPresenter extends BasePresenter<Object> {
    @Override
    protected Type getBeanType() {

        return new TypeToken<ResEntity<List<ShoppingCartBean>>>() {
        }.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.GET_SHORTCART_PRODUCTS_URL;
    }


    @Override
    protected boolean isList() {
        return true;
    }
}
