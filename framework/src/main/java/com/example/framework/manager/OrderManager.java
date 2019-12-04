package com.example.framework.manager;
/**
 * author:李浩帆
 */
public class OrderManager {
    //TODO 私有化对象
    private static OrderManager orderManager;

    //TODO 私有构造 单例模式
    private OrderManager() {
    }

    //TODO 提供公共的实例化方法
    public static OrderManager getInstance(){
        if(orderManager==null){
            orderManager = new OrderManager();
        }
        return orderManager;
    }
}
