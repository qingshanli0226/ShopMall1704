package com.example.common.port;

/**
 * author:李浩帆
 */
public interface IAccountCallBack {
    //TODO 注册成功
    void onRegisterSuccess();
    //TODO 登录成功
    void onLogin();
    //TODO 退出登录
    void onLogout();
    //TODO 更换头像
    void onAvatarUpdate(String url);
}
