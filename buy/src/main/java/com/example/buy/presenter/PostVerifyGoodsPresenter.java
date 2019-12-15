package com.example.buy.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.buy.databeans.GetCheckGoodsBean;
import com.example.buy.databeans.GoodsBean;
import com.example.common.utils.SignUtil;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *   发起多个库存请求
 */
public class PostVerifyGoodsPresenter extends BasePresenter<GetCheckGoodsBean> {
    //参数json串

    ArrayList<GoodsBean> list;

    public PostVerifyGoodsPresenter(ArrayList<GoodsBean> list) {
        this.list = list;
    }

    @Override
    public Object signJsonEncrypt() {
        JSONObject[] objects=new JSONObject[list.size()];
        for (int i=0;i<list.size();i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId",list.get(i).getProductId());
            jsonObject.put("productNum",list.get(i).getProductNum());
            jsonObject.put("productName",list.get(i).getProductName());
            jsonObject.put("url",list.get(i).getUrl());
            objects[i]=jsonObject;
        }
        return objects;
    }

    @Override
    public Type getBeanType() {
        return GetCheckGoodsBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECKINVENTORY;
    }

}
