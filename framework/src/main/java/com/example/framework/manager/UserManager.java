package com.example.framework.manager;

import android.content.Context;
import com.example.common.SPUtil;
import com.example.framework.User;
import com.example.framework.port.IUserCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * author:李浩帆
 */
public class UserManager {

    private SPUtil spUtil;
    private List<IUserCallBack> list = new ArrayList<>();

    //TODO 缓存一份用户信息
    public User user;
    /**
     *单例模式
     */
    //TODO 私有化对象
    private static UserManager userManager;
    //TODO 私有化构造
    private UserManager(Context context) {
        spUtil = new SPUtil();
        spUtil.init(context);
    }
    //TODO 获取UserManager实例
    public static UserManager getInstance(Context context){
        if(userManager ==null){
            userManager = new UserManager(context);
        }
        return userManager;
    }

    //TODO 是否登录
    public boolean isLogin(){
        return spUtil.isLogin();
    }

    //TODO 退出登录
    public void logout(){

    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    //TODO 登录成功存入token
    public void saveToken(String token){
        spUtil.saveToken(token);
    }

    //TODO 获取token
    public String getToken(){
        return spUtil.getToken();
    }

    //TODO 页面需要用到用户信息
    public void registerUserCallBack(IUserCallBack iUserCallBack){
        synchronized (list){
            if(!list.contains(iUserCallBack)){
                list.add(iUserCallBack);
            }
        }
    }

    //TODO 注销
    public void unRegisterUserCallBack(IUserCallBack iUserCallBack){
        synchronized (list){
            if(list.contains(iUserCallBack)){
                list.remove(iUserCallBack);
            }
        }
    }

    //TODO 注册成功后通知
    public void notifyRegisterSuccess() {
        synchronized (list) {
            for(IUserCallBack callback:list) {
                callback.onRegisterSuccess();
            }
        }
    }

    //TODO 登录成功后通知
    public void notifyLogin() {
        synchronized (list) {
            for(IUserCallBack callback:list) {
                callback.onLogin();
            }
        }
    }

    //TODO 更新头像后通知
    public void notifyUserAvatarUpdate(String path) {
        synchronized (list) {
            for(IUserCallBack callback:list) {
                callback.onAvatarUpdate(path);
            }
        }
    }

    //TODO 退出登录后通知
    public void notifyLogout() {
        synchronized (list) {
            for(IUserCallBack callback:list) {
                callback.onLogout();
            }
        }
    }

}
