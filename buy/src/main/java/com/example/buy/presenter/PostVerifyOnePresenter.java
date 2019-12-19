package com.example.buy.presenter;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.buy.databeans.OkBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品  GoodsActiviy  检查单个商品库存
 */

public class PostVerifyOnePresenter extends BasePresenter<OkBean> {

    String productId;
    int productNum;

    public PostVerifyOnePresenter(String productId, int productNum) {
        this.productId = productId;
        this.productNum = productNum;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("productId",productId);
        hashMap.put("productNum",productNum+"");
        return hashMap;

    }

    @Override
    public Type getBeanType() {
        return OkBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECKONPRODUCTINVENTORY;
    }
}
