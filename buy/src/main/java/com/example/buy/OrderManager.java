package com.example.buy;

public class OrderManager {
    public static void addOrder(){
        //直接发起未支付订单请求
    }
    public static void succeedOrder(){
        //支付成功后调用,把   未支付的订单   取消,改为支付成功订单
    }
    public static void cancelOrder(){
        //取消订单
    }
}
