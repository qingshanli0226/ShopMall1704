package com.example.buy.presenter;

import com.example.framework.base.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CheckPresenter extends BasePresenter<String> {

    private int productId;
    private int productNum;

    public CheckPresenter(int productId, int productNum) {
        this.productId = productId;
        this.productNum = productNum;
    }

    @Override
    protected Type getBeanType() {
        return null;
    }

    @Override
    protected String getPath() {
        return null;
    }

    @Override
    protected HashMap<String, String> getHeader() {
        return null;
    }

    @Override
    protected HashMap<String, String> getParam() {
        return null;
    }
}
