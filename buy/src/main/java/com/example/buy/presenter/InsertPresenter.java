package com.example.buy.presenter;

import com.example.framework.base.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

public class InsertPresenter extends BasePresenter {

    private String Config;
    private Type type;
    private HashMap<String, String> head;


    public InsertPresenter(String config, Type type, HashMap<String, String> head) {
        Config = config;
        this.type = type;
        this.head = head;
    }

    public InsertPresenter(String config, Type type) {
        Config = config;
        this.type = type;
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
    protected HashMap<String, String> getQuery() {
        return new HashMap<>();
    }
}
