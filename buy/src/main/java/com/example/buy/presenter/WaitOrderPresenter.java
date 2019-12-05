package com.example.buy.presenter;

import com.example.buy.databeans.GetOrderBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
/**
 * 订单P层  OrderActivity  发起待发货订单请求
 * */
public class WaitOrderPresenter extends BasePresenter {
    @Override
    public Type getBeanType() {
        return GetOrderBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.FINDFORSEND;
    }
}
