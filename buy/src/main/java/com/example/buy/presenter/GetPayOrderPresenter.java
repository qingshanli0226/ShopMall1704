package com.example.buy.presenter;

import com.example.buy.databeans.GetOrderBean;
import com.example.buy.databeans.GetPayBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 订单  OrderActivity  待支付订单
 * */
public class GetPayOrderPresenter extends BasePresenter<GetPayBean> {
    @Override
    public Type getBeanType() {
        return GetPayBean.class;
    }


    @Override
    public String getPath() {
        return AppNetConfig.FINDFORPAY;
    }
}
