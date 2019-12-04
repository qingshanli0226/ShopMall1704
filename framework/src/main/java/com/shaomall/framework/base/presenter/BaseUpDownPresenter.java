package com.shaomall.framework.base.presenter;

public abstract class BaseUpDownPresenter<T> extends BasePresenter<T> {

    public abstract String upLoad(String url);
    public abstract String downLoadFile(String url);
}
