package com.example.dimensionleague.address;

import com.example.buy.databeans.OkBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UpdateAddressPresenter extends BasePresenter<OkBean> {
    private String address;

    public UpdateAddressPresenter(String address) {
        this.address = address;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("address",address);
        return hashMap;
    }

    @Override
    public Type getBeanType() {
        return OkBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.UPDATEADDRESS;
    }
}
