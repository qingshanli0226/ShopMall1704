package com.example.framework.manager;

import com.example.common.port.IAccountCallBack;
import com.example.common.User;
import com.example.common.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author:李浩帆
 */
public class AccountManager {

    private List<IAccountCallBack> list = new ArrayList<>();

    //TODO 缓存一份用户信息
    public User user;
    /**
     *单例模式
     */
    //TODO 私有化对象
    private static AccountManager accountManager;
    //TODO 私有化构造
    private AccountManager() {

    }
    //TODO 获取UserManager实例
    public static AccountManager getInstance(){
        if(accountManager ==null){
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    //TODO 是否登录
    public boolean isLogin(){
        return SPUtil.isLogin();
    }

    //TODO 退出登录
    public void logout(){
        SPUtil.logout();
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    //TODO 登录成功存入token
    public void saveToken(String token){
        SPUtil.saveToken(token);
    }

    //TODO 获取token
    public String getToken(){
        return SPUtil.getToken();
    }

    //TODO 页面需要用到用户信息
    public void registerUserCallBack(IAccountCallBack iAccountCallBack){
        synchronized (list){
            if(!list.contains(iAccountCallBack)){
                list.add(iAccountCallBack);
            }
        }
    }

    //TODO 注销
    public void unRegisterUserCallBack(IAccountCallBack iAccountCallBack){
        synchronized (list){
            if(list.contains(iAccountCallBack)){
                list.remove(iAccountCallBack);
            }
        }
    }

    //TODO 注册成功后通知
    public void notifyRegisterSuccess() {
        synchronized (list) {
            for(IAccountCallBack callback:list) {
                callback.onRegisterSuccess();
            }
        }
    }

    //TODO 登录成功后通知
    public void notifyLogin() {
        synchronized (list) {
            for(IAccountCallBack callback:list) {
                callback.onLogin();
            }
        }
    }

    //TODO 更新头像后通知
    public void notifyUserAvatarUpdate(String path) {
        synchronized (list) {
            for(IAccountCallBack callback:list) {
                callback.onAvatarUpdate(path);
            }
        }
    }

    //TODO 退出登录后通知
    public void notifyLogout() {
        synchronized (list) {
            for(IAccountCallBack callback:list) {
                callback.onLogout();
            }
        }
    }
}
