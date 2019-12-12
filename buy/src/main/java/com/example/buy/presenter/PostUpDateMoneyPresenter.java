package com.example.buy.presenter;

import com.example.buy.databeans.OkBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PostUpDateMoneyPresenter extends BasePresenter<OkBean> {

    private String money;

    public PostUpDateMoneyPresenter(String money) {
        this.money = money;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("money",money);
        return hashMap;
    }


    @Override
    public Type getBeanType() {
        return OkBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.UPDATEMONEY;
    }
}
