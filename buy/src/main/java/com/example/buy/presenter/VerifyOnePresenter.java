package com.example.buy.presenter;

import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
/**
 * 商品P层   GoodsActiviy  单个商品库存请求
 * */

public class VerifyOnePresenter extends BasePresenter {

    String productId;
    int productNum;

    public VerifyOnePresenter(String productId, int productNum) {
        this.productId = productId;
        this.productNum = productNum;
    }

    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("productId",productId+"");
        hashMap.put("productNum",productNum+"");
        return hashMap;
    }

    @Override
    public Type getBeanType() {
        return String.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECKONPRODUCTINVENTORY;
    }
}
