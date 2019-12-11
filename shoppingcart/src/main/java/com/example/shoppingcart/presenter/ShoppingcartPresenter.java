package com.example.shoppingcart.presenter;

import android.os.UserManager;

import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.example.shoppingcart.Base.ShoppingCartBean;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import com.shaomall.framework.manager.UserInfoManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ShoppingcartPresenter extends BasePresenter<ShoppingCartBean> {

    @Override
    protected Type getBeanType() {

        return new TypeToken<ResEntity<List<ShoppingCartBean>>>() {}.getType();
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
