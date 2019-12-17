package com.example.shopmall.presenter;

import com.example.framework.base.BasePresenter;
import com.example.shopmall.bean.RegisterBean;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpImgPresenter extends BasePresenter {

    String uri;
    String token;

    public UpImgPresenter(String uri, String token) {
        this.uri = uri;
        this.token = token;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<RegisterBean>() {
        }.getType();
    }

    @Override
    protected String getPath() {
        return "upload";
    }

    @Override
    protected HashMap<String, String> getHeader() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);
        return hashMap;
    }

    @Override
    protected HashMap<String, String> getParam() {
        return new HashMap<>();
    }

    @Override
    public MultipartBody.Part getFile() {
        File file = new File(uri);
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return body;
    }
}
