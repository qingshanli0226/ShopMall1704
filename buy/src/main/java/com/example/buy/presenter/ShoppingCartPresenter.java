package com.example.buy.presenter;

import com.example.buy.bean.ShoppingCartBean;
import com.example.framework.base.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

public class ShoppingCartPresenter extends BasePresenter<ShoppingCartBean> {

    private final String Config;
    private final Type type;
    private final HashMap<String, String> head;

    public ShoppingCartPresenter(String config, Type type, HashMap<String, String> head) {
        Config = config;
        this.type = type;
        this.head = head;
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
}
