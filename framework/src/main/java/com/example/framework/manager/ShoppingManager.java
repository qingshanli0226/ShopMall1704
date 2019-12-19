package com.example.framework.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingManager {

    private static ShoppingManager shoppingManager;
    //购物车内选中的物品总价
    private double allCount = 0;
    //全选按钮是否被选中
    private boolean isSetting = false;
    //购物车内物品（本地）
    private List<Map<String, String>> data = new ArrayList<>();
    //购物车内选中的购买物品
    private List<Map<String, String>> data2 = new ArrayList<>();

    //购物车内改变前和改变后的数目
    private int beforenum = 0;
    private int afternum = 0;

    //控制返回时的主页面item
    private int mainitem = 0;

    //数目变化监听
    private OnNumberChangedListener onNumberChangedListener;

    public ShoppingManager() {
    }

    public static ShoppingManager getInstance() {
        if (shoppingManager == null) {
            synchronized (ShoppingManager.class) {
                if (shoppingManager == null) {
                    shoppingManager = new ShoppingManager();
                }
            }
        }
        return shoppingManager;
    }

    public int getMainitem() {
        return mainitem;
    }

    public void setMainitem(int mainitem) {
        this.mainitem = mainitem;
    }

    public int getBeforenum() {
        return beforenum;
    }

    public void setBeforenum(int beforenum) {
        this.beforenum = beforenum;
    }

    public int getAfternum() {
        return afternum;
    }

    public List<Map<String, String>> getBuyThings() {
        return data2;
    }

    public void setBuyThings(List<Map<String, String>> data2) {
        this.data2 = data2;
    }

    public double getAllMoney() {
        return allCount;
    }

    public int getAllNumber() {
        int nums = 0;
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map = data.get(i);
            String num = map.get("num");
            int i1 = Integer.parseInt(num);
            nums += i1;
        }
        return nums;
    }

    public void setAfternum(int afternum) {
        this.afternum = afternum;
    }

    public void setOnNumberChanged(int x) {
        if (onNumberChangedListener != null) {
            onNumberChangedListener.NumberChanged(getAllNumber() + x);
        }
    }

    public void setOnNumberChangedListener(OnNumberChangedListener onNumberChangedListener) {
        this.onNumberChangedListener = onNumberChangedListener;
    }

    public interface OnNumberChangedListener {
        void NumberChanged(int num);
    }

    public boolean getisSetting() {
        return isSetting;
    }

    public double getAllCount() {
        return allCount;
    }

    public void setAllCount(double allCount) {
        this.allCount = allCount;
    }

    public int getAllChecked() {
        int nums = 0;
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map = data.get(i);
            String ischecked = map.get("ischecked");
            if (ischecked.equals("true")) {
                nums++;
            }
        }
        return nums;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public void setisSetting(boolean isSetting) {
        this.isSetting = isSetting;
    }

    public String getToken(Context context) {
        SharedPreferences getToken = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String getToken1 = getToken.getString("getToken", null);
        return getToken1;
    }


}
