package com.example.buy.presenter;

import com.example.buy.databeans.GetOrderBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
/**
 * 订单  OrderActivity  待支付订单
 * */
public class GetPayOrderPresenter extends BasePresenter<GetOrderBean> {
    @Override
    public Type getBeanType() {
        return GetOrderBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.FINDFORPAY;
    }
}
