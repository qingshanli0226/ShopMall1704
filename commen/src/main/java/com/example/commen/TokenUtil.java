package com.example.commen;

import android.content.SharedPreferences;

public class TokenUtil {
    public static SharedPreferences sharedPreferences = null;

    public static String getToken(){
        if (sharedPreferences != null){
            return sharedPreferences.getString("token", null);
        }
        return null;
    }
}
