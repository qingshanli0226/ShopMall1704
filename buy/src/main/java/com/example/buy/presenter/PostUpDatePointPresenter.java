package com.example.buy.presenter;

import com.example.buy.databeans.OkBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
/**
 * 积分更新
 * */
public class PostUpDatePointPresenter extends BasePresenter<OkBean> {
    private String point;

    public PostUpDatePointPresenter(String point) {
        this.point = point;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("point",point);
        return hashMap;
    }

    @Override
    public Type getBeanType() {
        return OkBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.UPDATEPOINT;
    }
}
