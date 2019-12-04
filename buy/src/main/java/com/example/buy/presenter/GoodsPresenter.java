package com.example.buy.presenter;

import com.example.buy.databeans.GoodsBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GoodsPresenter extends BasePresenter<GoodsBean> {
    //参数json串

    ArrayList<GoodsBean> list;

    public GoodsPresenter(ArrayList<GoodsBean> list) {
        this.list = list;
    }

    @Override
    public Object getJsonParams() {
        return list;
    }

    @Override
    public Type getBeanType() {
        return GoodsBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECKINVENTORY;
    }
}
