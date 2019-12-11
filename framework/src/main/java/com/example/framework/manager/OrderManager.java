package com.example.framework.manager;

import com.example.framework.listener.OnOrderListener;
import com.example.framework.listener.OnShopCartListener;

import java.util.LinkedList;

/**
 * author:李浩帆
 */
public class OrderManager {
    LinkedList<OnOrderListener> listeners=new LinkedList<>();

    private static OrderManager orderManager=new OrderManager();

    private OrderManager() {
    }

    public static OrderManager getInstance(){
        return orderManager;
    }

    public void registerListener(OnOrderListener onOrderListener){
        listeners.add(onOrderListener);
    }
    public void unregister(OnOrderListener onOrderListener){
        listeners.remove(onOrderListener);
    }
    public void updateWaitOrederNum(int num){
        for (int i=0;i<listeners.size();i++){
            listeners.get(i).waitOrderChange(num);
        }
    }
    public void updateAllOrederNum(int num){
        for (int i=0;i<listeners.size();i++){
            listeners.get(i).allOrderChange(num);
        }
    }
    public void updatePayOrederNum(int num){
        for (int i=0;i<listeners.size();i++){
            listeners.get(i).payOrderChange(num);
        }
    }
}
