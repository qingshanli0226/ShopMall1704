package com.example.buy.presenter;

import com.example.buy.databeans.GoodsBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;

public class GoodsPresenter extends BasePresenter<GoodsBean> {
    @Override
    public Type getBeanType() {
        return GoodsBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECKINVENTORY;
    }
}
