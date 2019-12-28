package com.example.buy;

import android.content.Context;

public class GoodsManager {
    private static GoLoginListener goLoginListener;

    public static void setGoLoginListener(GoLoginListener goLoginListener) {
        GoodsManager.goLoginListener = goLoginListener;
    }

    public static void gotoLogin(Context context){
        goLoginListener.onClickButton(context);
    }
}
