package com.example.buy.presenter;

import com.example.buy.bean.InsertBean;
import com.example.framework.base.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class InsertPresenter extends BasePresenter<InsertBean> {

    private String Config;
    private Type type;
    private HashMap<String, String> head;
    private String s;

    public InsertPresenter(String config, Type type, HashMap<String, String> head) {
        Config = config;
        this.type = type;
        this.head = head;
    }

    public InsertPresenter(String config, Type type) {
        Config = config;
        this.type = type;
    }

    public InsertPresenter(String config, Type type, HashMap<String, String> hashMap, String s) {
        this.Config = config;
        this.type = type;
        this.head = hashMap;
        this.s = s;
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
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);
        return body;
    }
}
