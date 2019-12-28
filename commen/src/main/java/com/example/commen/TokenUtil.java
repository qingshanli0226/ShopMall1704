package com.example.commen;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenUtil {
    private static TokenUtil instance;
    private SharedPreferences userInfo;


    public static TokenUtil getInstance() {
        if (instance == null) {
            synchronized (TokenUtil.class) {
                if (instance == null) {
                    instance = new TokenUtil();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        if (userInfo == null) {
            userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        }
    }

    public String getToken() {
        if (userInfo == null) {
            throw new RuntimeException("TokenUtil.getInstance().init()没有初始化");
        }
        return userInfo.getString("token", null);
    }
}
