package com.example.shopmall.presenter;

import com.example.common.SignUtil;
import com.example.framework.base.BasePresenter;
import com.example.framework.bean.LoginBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动登录
 */
class AutomaticPresenter extends BasePresenter<LoginBean> {

    private final String token;

    public AutomaticPresenter(String token) {
        this.token = token;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<LoginBean>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        return "autoLogin";
    }

    @Override
    protected HashMap<String, String> getHeader() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);
        return new HashMap<>();
    }

    @Override
    protected Map<String, String> getParam() {
        //请求加密
        Map<String, String> parmMap = SignUtil.getEmptyTreeMap();
        parmMap.put("token", token);
        String sign = SignUtil.generateSign(parmMap);//生成签名
        parmMap.put("sign", sign);
        return SignUtil.encryptParamsByBase64(parmMap);
    }
}
