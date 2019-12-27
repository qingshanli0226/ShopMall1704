package com.example.buy.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.framework.base.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PayPresenter extends BasePresenter {

    private final String Config;
    private final Type type;
    private final HashMap<String, String> head;
    private final JSONObject body;

    public PayPresenter(String config, Type type, HashMap<String, String> head, JSONObject body) {
        Config = config;
        this.type = type;
        this.head = head;
        this.body = body;
    }

    @Override
    protected Type getBeanType() {
        return type;
    }

    @Override
    protected String getPath() {
        return Config;
    }

    @Override
    protected HashMap<String, String> getHeader() {
        return head;
    }

    @Override
    protected HashMap<String, String> getParam() {
        return new HashMap<>();
    }

    @Override
    protected RequestBody getRequestBody() {
        Log.e("####", body.toString());
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString());
    }
}
