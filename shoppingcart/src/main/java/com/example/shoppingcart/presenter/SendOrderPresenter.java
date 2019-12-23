package com.example.shoppingcart.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.example.shoppingcart.bean.OrderInfoBean;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import com.shaomall.framework.bean.ShoppingCartBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SendOrderPresenter extends BasePresenter<Object> {
    private ArrayList<ShoppingCartBean> list;
    private Float totalPrice;

    public SendOrderPresenter(ArrayList<ShoppingCartBean> list, float totalPrice) {
        this.list = list;
        this.totalPrice = totalPrice;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<OrderInfoBean>>() {
        }.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.GET_ORDER_INFO_URL;
    }

    @Override
    protected JSONObject getJsonParam() {
        JSONObject object = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (ShoppingCartBean i : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productId", i.getProductId());
                jsonObject.put("productName", i.getProductName());
                jsonArray.add(jsonObject);
            }
            object.put("subject", "购买");
            object.put("totalPrice", totalPrice);
            object.put("body", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
