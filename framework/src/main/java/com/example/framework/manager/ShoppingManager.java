package com.example.framework.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.example.framework.bean.ResultBean;

import java.io.File;
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
    //收藏物品
    private List<Map<String, String>> data3 = new ArrayList<>();
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

    public List<Map<String, String>> getCollections(Context context) {
        SharedPreferences collection = context.getSharedPreferences("collection", Context.MODE_PRIVATE);
        ResultBean user = UserManager.getInstance().getUser();
        String name = user.getName();
        String collections = collection.getString(name+"collections", null);
        if(collections!=null){
            String[] s = collections.split("///");
            data3.clear();
            for (int i = 0; i < s.length; i++) {
                String s1 = s[i];
                String[] split = s1.split(":");
                Log.e("####",s1);
                Map<String,String> map = new HashMap<>();
                map.put("id",split[0]);
                map.put("title",split[1]);
                map.put("price",split[2]);
                map.put("img",split[3]);
                map.put("num",split[4]);
                data3.add(map);
                Log.e("####",map.toString());
            }
        }
        return data3;
    }

    @SuppressLint("CommitPrefEdits")
    public void setCollections(Context context, List<Map<String, String>> data3) {
        this.data3 = data3;
        ResultBean user = UserManager.getInstance().getUser();
        String name = user.getName();
        SharedPreferences collection = context.getSharedPreferences("collection", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = collection.edit();
        StringBuffer buffer = new StringBuffer();
        Map<String, String> map = data3.get(0);
        String id = map.get("id");
        String title = map.get("title");
        String price = map.get("price");
        String img = map.get("img");
        String num = map.get("num");
        buffer.append(id+":"+title+":"+price+":"+img+":"+num);
        for (int i = 1; i < data3.size(); i++) {
            Map<String, String> map1 = data3.get(i);
            String id1 = map1.get("id");
            String title1 = map1.get("title");
            String price1 = map1.get("price");
            String img1 = map1.get("img");
            String num1 = map1.get("num");
            buffer.append("///"+id1+":"+title1+":"+price1+":"+img1+":"+num1);
        }
        edit.putString(name+"collections",buffer.toString());
        edit.apply();
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
