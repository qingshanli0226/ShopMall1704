package com.example.buy.presenter;

import com.example.buy.databeans.GetCartBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;

public class CartPresenter extends BasePresenter<GetCartBean> {
    @Override
    public Type getBeanType() {
        return GetCartBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.GETSHORTCARTPRODUCTS;
    }
}
