package com.example.buy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingUtils {

    private static ShoppingUtils shoppingUtils;
    double allCount = 0;
    boolean isSetting = false;
    private List<Map<String, String>> data = new ArrayList<>();
    private List<Map<String, String>> data2 = new ArrayList<>();

    public ShoppingUtils() {
    }

    public List<Map<String, String>> getBuyThings() {
        return data2;
    }

    public void setBuyThings(List<Map<String, String>> data2) {
        this.data2 = data2;
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

    public void joinintoShoppingCart(Map<String, String> map) {
        data.add(map);
    }
}
