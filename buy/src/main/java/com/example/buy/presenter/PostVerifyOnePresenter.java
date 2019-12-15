package com.example.buy.presenter;

import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * 商品  GoodsActiviy  检查单个商品库存
 */

public class PostVerifyOnePresenter extends BasePresenter {

    String productId;
    int productNum;

    public PostVerifyOnePresenter(String productId, int productNum) {
        this.productId = productId;
        this.productNum = productNum;
    }

    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("productId", productId + "");
        hashMap.put("productNum", productNum + "");
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

    @Override
    public void detachView() {
        super.detachView();
    }
}
