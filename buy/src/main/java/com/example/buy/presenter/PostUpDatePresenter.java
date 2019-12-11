package com.example.buy.presenter;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
/**
 * 购物车P层  ShopCartFragment  更新购物车数量
 * */
public class PostUpDatePresenter extends BasePresenter<GoodsBean> {
    GoodsBean goodsBean;

    public PostUpDatePresenter(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    @Override
    public JSONObject getJsonParams() {
        JSONObject object = new JSONObject();
        try {
            object.put("productId", goodsBean.getProductId());
            object.put("productNum", goodsBean.getProductNum());
            object.put("productName", goodsBean.getProductName());
            object.put("url", goodsBean.getUrl());
            object.put("productPrice",goodsBean.getProductPrice());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
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
