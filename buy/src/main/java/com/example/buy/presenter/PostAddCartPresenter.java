package com.example.buy.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.common.manager.AccountManager;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 商品P层   GoodsActiviy  向服务器申请加入购物车
 * */
public class PostAddCartPresenter extends BasePresenter<OkBean> {

    GoodsBean goodsBean;

    public PostAddCartPresenter(GoodsBean goodsBean) {
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
        return AppNetConfig.ADDONEPRODUCT;
    }
}
