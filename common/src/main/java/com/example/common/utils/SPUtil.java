package com.example.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.Constant;

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
        return sp.getBoolean(Constant.IS_LOGIN,false);
    }

    //TODO 退出登录
    public static void logout(){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(Constant.IS_LOGIN,false);
        edit.apply();
    }

    //TODO 存储注册签名
    public static void setSign(String sign){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Constant.SIGN,sign);
        edit.apply();
    }

    //TODO 获取签名
    public static String getSign(){
        return sp.getString(Constant.SIGN,"");
    }
}
