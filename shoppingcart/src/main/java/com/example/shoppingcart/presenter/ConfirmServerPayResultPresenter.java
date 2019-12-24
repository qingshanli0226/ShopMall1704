package com.example.shoppingcart.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.example.shoppingcart.pay.PayMessage;
import com.example.shoppingcart.pay.PayResult;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.nio.file.Path;

public class ConfirmServerPayResultPresenter extends BasePresenter<Object> {
    private final String outTradeNo;
    private final String resultContent;
    private final boolean payResultIsOk;

    public ConfirmServerPayResultPresenter(String outTradeNo, String resultContent, boolean payResultIsOk) {
        this.outTradeNo = outTradeNo;
        this.resultContent = resultContent;
        this.payResultIsOk = payResultIsOk;
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
        jsonObject.put("clientPayResult", payResultIsOk);
        return jsonObject;
    }
}
