package com.example.buy.presenter;

import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
/**
 * 购物车P层  ShopCartFragment  更新购物车数量
 * */
public class UpDatePresenter extends BasePresenter<GoodsBean> {
    GoodsBean goodsBean;

    public UpDatePresenter(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    @Override
    public Type getBeanType() {
        return OkBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.UPDATEPRODUCTNUM;
    }
}
