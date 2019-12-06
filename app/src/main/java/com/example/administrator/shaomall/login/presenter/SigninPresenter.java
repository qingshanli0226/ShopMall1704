package com.example.administrator.shaomall.login.presenter;

import com.example.administrator.shaomall.login.Base.SigninBean;
import com.example.net.AppNetConfig;
import com.example.net.ResEntity;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.base.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SigninPresenter extends BasePresenter<SigninBean> {
    String username=null;
    String password=null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Type getBeanType() {
        return new TypeToken<ResEntity<SigninBean>>(){}.getType();
    }

    @Override
    public String getPath() {
        return AppNetConfig.REGISTER_URL;
    }

    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> map = new HashMap<>();
        if (getUsername() !=null || getPassword() !=null){
            map.put("name",getUsername());
            map.put("password",getPassword());

        }

        return map;
    }
}
