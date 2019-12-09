package com.example.administrator.shaomall.login.presenter;

import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;
import com.shaomall.framework.bean.LoginBean;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LoginPresenter extends BasePresenter<LoginBean> {


    String username=null;
    String passname=null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassname() {
        return passname;
    }

    public void setPassname(String passname) {
        this.passname = passname;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<LoginBean>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.LOGIN_URL;
    }

    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> map = new HashMap<>();

        if (username != null || passname != null){
            map.put("name",getUsername());
            map.put("password",getPassname());
        }
        return map;
    }


}
