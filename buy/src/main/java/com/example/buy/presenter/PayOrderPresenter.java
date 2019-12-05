package com.example.buy.presenter;

import com.example.buy.databeans.GetOrderBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;

public class PayOrderPresenter extends BasePresenter {
    @Override
    public Type getBeanType() {
        return GetOrderBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.FINDFORPAY;
    }
}
