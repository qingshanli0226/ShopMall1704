package com.example.step;

import android.icu.text.UnicodeSet;
import android.util.Log;

import com.example.framework.base.BasePresenter;
import com.example.framework.manager.UserManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class PointBresenter extends BasePresenter<PonitBean> {

    int point;

    public PointBresenter( int point) {
        this.point = point;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<PonitBean>(){}.getType();
    }

    @Override
    protected String getPath() {
        return "updatePoint";
    }

    @Override
    protected HashMap<String, String> getHeader() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", UserManager.getInstance().getToken());
        return hashMap;
    }

    @Override
    protected HashMap<String, String> getParam() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("point",point+"");
        return hashMap;
    }
}
