package com.example.buy.presenter;

import com.example.buy.databeans.GetPayOrderBean;
import com.example.buy.databeans.SendOrdersBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;

public class PostOrderPresenter extends BasePresenter<GetPayOrderBean> {

    SendOrdersBean sendOrdersBean;

    public PostOrderPresenter(SendOrdersBean sendOrdersBean) {
        this.sendOrdersBean = sendOrdersBean;
    }

    @Override
    public Object getJsonParams() {
        return sendOrdersBean;
    }

    @Override
    public Type getBeanType() {
        return GetPayOrderBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.UPDATEPOINT;
    }
}
