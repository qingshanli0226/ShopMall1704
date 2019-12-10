package com.example.framework.listener;

public interface OnOrderListener {
    //待发货
    void waitOrderChange(int num);
    //全部
    void allOrderChange(int num);
    //待支付
    void payOrderChange(int num);
}
