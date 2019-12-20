package com.example.shopmall.presenter;

import android.content.Context;

import com.example.framework.base.BasePresenter;
import com.example.framework.bean.ResultBean;
import com.example.framework.manager.UserManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class DownLoadImgPresenter extends BasePresenter {

    private String token;
    private Context context;

    public DownLoadImgPresenter(Context context) {
        this.context = context;
    }

    public DownLoadImgPresenter(String token) {
        this.token = token;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResponseBody>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        ResultBean user = UserManager.getInstance().getUser();
        String avatar = user.getAvatar();
        return "http://49.233.93.155:8080" + avatar;
    }

    @Override
    protected HashMap<String, String> getHeader() {
        return new HashMap<>();
    }

    @Override
    protected HashMap<String, String> getParam() {
        return new HashMap<>();
    }
}
