package com.example.buy.presenter;

import com.example.buy.databeans.GetSearchBeanOne;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GetSearchPresenter extends BasePresenter {
    private String name;

    public GetSearchPresenter(String name) {
        this.name = name;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name",name);
        return hashMap;
    }

    @Override
    public Type getBeanType() {
        return null;
    }

    @Override
    public String getPath() {
        return AppNetConfig.SEARCH;
    }
}
