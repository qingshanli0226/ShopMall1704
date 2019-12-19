package com.example.shoppingcart.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;

public class ConfirmServerPayResultPresenter extends BasePresenter<String> {
    private final String outTradeNo;
    private final String resultContent;

    public ConfirmServerPayResultPresenter(String outTradeNo, String resultContent) {
        this.outTradeNo = outTradeNo;
        this.resultContent = resultContent;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<String>>() {
        }.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.CONFIRM_SERVER_PAY_RESULT_URL;
    }

    @Override
    protected JSONObject getJsonParam() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("outTradeNo", outTradeNo);
        jsonObject.put("result", resultContent);
        return jsonObject;
    }
}
