package com.example.administrator.shaomall.search;

import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import com.shaomall.framework.bean.SearchBean;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPresenter extends BasePresenter<SearchBean> {
    private String name;

    public SearchPresenter(String name) {
        this.name = name;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<List<SearchBean>>>(){}.getType();
//        return new TypeToken<ResEntity<SearchBean>>(){}.getType();
    }

    @Override
    protected boolean isList() {
        return true;
    }

    @Override
    public String getPath() {
        return AppNetConfig.SEARCH_URL;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name",name);
        return stringStringHashMap;
    }
}
