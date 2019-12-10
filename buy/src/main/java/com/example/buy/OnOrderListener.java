package com.example.buy;

public interface OnOrderListener {
    void OnAllOrderListener(int num);
    void OnPayOrderListener(int num);
    void OnWaitOrderListener(int num);
}
