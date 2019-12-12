package com.example.point;

import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;

public class PointPresenter  extends BasePresenter {
    String point;

    public PointPresenter(String point) {
        this.point = point;
    }
    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("point",point+"");
        return hashMap;
    }

    @Override
    public Type getBeanType() {
        return String.class;
    }

    @Override
    public String getPath() {
        return  AppNetConfig.UPDATEPOINT;
    }
}
