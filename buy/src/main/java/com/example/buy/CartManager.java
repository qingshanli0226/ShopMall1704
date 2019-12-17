package com.example.buy;

import com.example.buy.databeans.CheckGoodsData;
import com.example.buy.databeans.GoodsBean;
import com.example.framework.listener.OnShopCartListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class CartManager {
    private LinkedList<OnShopCartListener> listeners=new LinkedList<>();

    //数据和被选择
    private ArrayList<GoodsBean> list = new ArrayList<>();
    private ArrayList<CheckGoodsData> checks = new ArrayList<>();

    private static CartManager cartManager=new CartManager();

    private CartManager() {
    }

    public static CartManager getInstance(){
        return cartManager;
    }

    public void registerListener(OnShopCartListener onShopCartListener){
        listeners.add(onShopCartListener);
    }
    public void unregister(OnShopCartListener onShopCartListener){
        listeners.remove(onShopCartListener);
    }

    public int getCartNum(){
        return list.size();
    }

    public ArrayList<GoodsBean> getList() {
        return list;
    }

    public void setList(ArrayList<GoodsBean> list) {
        this.list = list;
        for (int i=0;i<listeners.size();i++){
            listeners.get(i).shopCartNumChange(list.size());
        }
    }

    public ArrayList<CheckGoodsData> getChecks() {
        return checks;
    }

    public void setChecks(ArrayList<CheckGoodsData> checks) {
        this.checks = checks;
    }
}
