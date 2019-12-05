package com.example.buy.presenter;

import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
/**
 * 商品P层   GoodsActiviy  向服务器申请加入购物车
 * */
public class AddCartPresenter extends BasePresenter<OkBean> {

    GoodsBean goodsBean;

    public AddCartPresenter(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    @Override
    public Object getJsonParams() {
        return goodsBean;
    }

    @Override
    public Type getBeanType() {
        return OkBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.ADDONEPRODUCT;
    }
}
