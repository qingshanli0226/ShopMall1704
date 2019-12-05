package com.example.buy.presenter;

import com.example.buy.databeans.GetCheckGoodsBean;
import com.example.buy.databeans.GoodsBean;
import com.example.framework.base.BasePresenter;
import com.example.net.AppNetConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
/**
 * 购物车P层  ShopCartFragment  发起库存请求
 * */
public class PostVerifyGoodsPresenter extends BasePresenter<GetCheckGoodsBean> {
    //参数json串

    ArrayList<GoodsBean> list;

    public PostVerifyGoodsPresenter(ArrayList<GoodsBean> list) {
        this.list = list;
    }

    @Override
    public Object getJsonParams() {
        return list;
    }

    @Override
    public Type getBeanType() {
        return GetCheckGoodsBean.class;
    }

    @Override
    public String getPath() {
        return AppNetConfig.CHECKINVENTORY;
    }

}
