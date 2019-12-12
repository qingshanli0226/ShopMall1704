package com.example.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.common.code.Constant;

/**
 * author:李浩帆
 */
public class SPUtil {
    private static final String NAME = "account";
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    //TODO 保存token
    public static void saveToken(String token) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.TOKEN, token);
        editor.apply();
    }

    //TODO 读取token
    public static String getToken() {
        return sp.getString(Constant.TOKEN, "");
    }

    //TODO 是否登录
    public static boolean isLogin(){
        return !TextUtils.isEmpty(getToken());
    }

    //TODO 退出登录
    public static void logout(){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Constant.TOKEN,"");
        edit.apply();
    }
}
