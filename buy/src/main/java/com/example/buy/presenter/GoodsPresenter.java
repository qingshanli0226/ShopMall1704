package com.example.buy.presenter;

import com.example.buy.databeans.GoodsBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class GoodsPresenter extends BasePresenter<GoodsBean> {
    @Override
    public Type getBeanType() {
        return GoodsBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECKINVENTORY;
    }

}
