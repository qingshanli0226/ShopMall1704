package com.example.buy.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.buy.databeans.OkBean;
import com.example.buy.databeans.SendPayResultBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
/**
 *
 * */

public class PostPayResultPresenter extends BasePresenter<OkBean> {
    SendPayResultBean sendPayResultBean;

    public PostPayResultPresenter(SendPayResultBean sendPayResultBean) {
        this.sendPayResultBean = sendPayResultBean;
    }

    @Override
    public JSONObject getJsonParams() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("clientPayResult", true);
        jsonObject.put("outTradeNo", sendPayResultBean.getOutTradeNo());
        jsonObject.put("result", sendPayResultBean.getResult());
        return jsonObject;
    }

    @Override
    public Type getBeanType() {
        return OkBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CONFIRMSERVERPAYRESULT;
    }
}
