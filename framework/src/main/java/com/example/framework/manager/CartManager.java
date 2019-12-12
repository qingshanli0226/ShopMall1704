package com.example.framework.manager;

import com.example.framework.listener.OnShopCartListener;

import java.util.LinkedList;

public class CartManager {
    private int num=0;
    private LinkedList<OnShopCartListener> listeners=new LinkedList<>();

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
    public void updataCartNum(int newNum){
        num=newNum;
        for (int i=0;i<listeners.size();i++){
            listeners.get(i).shopCartNumChange(num);
        }
    }
    public int getCartNum(){
        return num;
    }
}
