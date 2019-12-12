package com.example.administrator.shaomall.mine;


import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 更新积分
 */
public class PointUpLoadPresenter extends BasePresenter<String> {


    private int pointSum;
    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<String>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.UPDATE_POINT_URL;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put("point", getPointSum()+"");

        return map;
    }

    public int getPointSum() {
        return pointSum;
    }

    public void setPointSum(int pointSum) {
        this.pointSum = pointSum;
    }
}
