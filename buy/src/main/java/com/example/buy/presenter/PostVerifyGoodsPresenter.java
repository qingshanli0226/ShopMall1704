package com.example.buy.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.buy.databeans.GetCheckGoodsBean;
import com.example.buy.databeans.GoodsBean;
import com.example.common.utils.SignUtil;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 购物车P层  ShopCartFragment  发起库存请求
 */
public class PostVerifyGoodsPresenter extends BasePresenter<GetCheckGoodsBean> {
    //参数json串

    ArrayList<GoodsBean> list;

    public PostVerifyGoodsPresenter(ArrayList<GoodsBean> list) {
        this.list = list;
    }

    @Override
    public Object signJsonEncrypt() {
        String[] strs=new String[list.size()];
        for (int i=0;i<list.size();i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId",list.get(i).getProductId());
            jsonObject.put("productNum",list.get(i).getProductNum());
            jsonObject.put("productName",list.get(i).getProductName());
            jsonObject.put("url",list.get(i).getUrl());
            strs[i]= SignUtil.generateJsonSign(jsonObject);
        }
        return strs;
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
