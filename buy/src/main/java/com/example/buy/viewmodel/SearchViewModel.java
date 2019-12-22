package com.example.buy.viewmodel;

import com.example.framework.base.BaseViewModel;
import com.example.net.AppNetConfig;
import java.util.HashMap;
import java.util.Map;

public class SearchViewModel extends BaseViewModel {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name",name);
        return hashMap;
    }

    @Override
    public String getPath() {
        return AppNetConfig.SEARCH;
    }
}
