package com.example.buy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingUtils {

    private static ShoppingUtils shoppingUtils;
    int allCount = 0;
    boolean isSetting = false;
    private List<Map<String, String>> data = new ArrayList<>();

    public ShoppingUtils() {
    }

    public static ShoppingUtils getInstance() {
        if (shoppingUtils == null) {
            synchronized (ShoppingUtils.class) {
                if (shoppingUtils == null) {
                    shoppingUtils = new ShoppingUtils();
                }
            }
        }

        return shoppingUtils;
    }

    public int getAllMoney() {
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

    public boolean getisSetting() {
        return isSetting;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
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
}
