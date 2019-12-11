package com.example.buy.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.example.buy.databeans.GetPayOrderBean;
import com.example.buy.databeans.SendOrdersBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

import okhttp3.RequestBody;

public class PostOrderPresenter extends BasePresenter<GetPayOrderBean> {

    SendOrdersBean sendOrdersBean;

    public PostOrderPresenter(SendOrdersBean sendOrdersBean) {
        this.sendOrdersBean = sendOrdersBean;
    }

    @Override
    public JSONObject getJsonParams() {
        JSONObject object = new JSONObject();
        try {
            object.put("subject", sendOrdersBean.getSubject());
            object.put("totalPrice", sendOrdersBean.getTotalPrice());
            JSONArray jsonArray=new JSONArray();
            for (SendOrdersBean.BodyBean i:sendOrdersBean.getBody()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productId",i.getProductId());
                jsonObject.put("productName",i.getProductName());
                jsonArray.add(jsonObject);
            }
            object.put("body", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
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
