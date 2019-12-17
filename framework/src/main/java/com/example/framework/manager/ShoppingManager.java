package com.example.framework.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingManager {

    private static ShoppingManager shoppingManager;
    private double allCount = 0;
    private boolean isSetting = false;
    private List<Map<String, String>> data = new ArrayList<>();
    private List<Map<String, String>> data2 = new ArrayList<>();

    int beforenum = 0;
    int afternum = 0;

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

    private OnNumberChangedListener onNumberChangedListener;

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

    public void initializeDatas() {

        for (int i = 0; i < data2.size(); i++) {
            Map<String, String> map = data2.get(i);
            for (int j = 0; j < data.size(); j++) {
                Map<String, String> map1 = data.get(j);
                if (map.get("title").equals(map1.get("title"))) {
                    data.remove(j);
                    j--;
                }
            }
        }

        data2.clear();
        allCount = 0;
        isSetting = false;
    }

    public String getToken(Context context) {
        SharedPreferences getToken = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String getToken1 = getToken.getString("getToken", null);
        return getToken1;
    }

    public void joinintoShoppingCart(Map<String, String> map) {
        data.add(map);
    }
}
