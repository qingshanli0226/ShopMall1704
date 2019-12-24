package com.example.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.common.code.Constant;

import java.util.ArrayList;

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
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    //TODO 退出登录
    public static void logout() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(Constant.TOKEN, "");
        edit.apply();
    }

    public static void puSearch(String searchStr) {
        for (int i = 10; i > -1; i--) {
            if (sp.getString(Constant.SEARCH + i, "").isEmpty()) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(Constant.SEARCH + i, searchStr);
                edit.commit();
                return;
            }
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(searchStr);
        for (int i = 1; i < 10; i++) {
            list.add(sp.getString(Constant.SEARCH + (i-1), ""));
        }
        for (int i = 9; i > -1; i--) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(Constant.SEARCH + i, list.get(i));
            edit.commit();
        }
    }

    public static ArrayList<String> getSearch() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (!sp.getString(Constant.SEARCH + i, "").isEmpty()) {
                list.add(sp.getString(Constant.SEARCH + i, ""));
            }
        }
        return list;
    }
}
