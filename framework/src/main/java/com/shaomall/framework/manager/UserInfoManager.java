package com.shaomall.framework.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.commen.ACache;
import com.example.commen.bean.LoginBean;

import java.util.LinkedList;

public class UserInfoManager {
    private        Context                            mContext;
    private static UserInfoManager                    instance;
    private        ACache                             aCache;
    private        SharedPreferences                  sharedPreferences;
    private        LinkedList<UserInfoStatusListener> userInfoStatusListeners = new LinkedList<>();

    private UserInfoManager() {

    }

    public static UserInfoManager getInstance() {
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    instance = new UserInfoManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context, ACache aCache) {
        this.mContext = context;
        this.aCache = aCache;
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }


    /**
     * 用户登录状态
     *
     * @return
     */
    public boolean isLogin() {
        return sharedPreferences.getBoolean("isLogin", false);
    }

    /**
     * 退出登录
     */
    public void unLogout() {
        if (isLogin()) { //登录状态
            aCache.remove("userInfo");
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove("isLogin");
            edit.apply();


            //退出登录监听
            for (UserInfoStatusListener listener : userInfoStatusListeners) {
                listener.onUserStatus(isLogin(), readUserInfo());
            }
        }
    }

    /**
     * 保存用户信息
     *
     * @param dataBean
     */
    public void saveUserInfo(LoginBean dataBean) {

        if (aCache != null) {
            aCache.put("userInfo", dataBean); //缓存用户信息
            //设置登录状态
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("isLogin", true);
            edit.apply();

            //登录状态监听
            for (UserInfoStatusListener listener : userInfoStatusListeners) {
                listener.onUserStatus(isLogin(), readUserInfo());
            }
        }
    }


    /**
     * 读取用户信息
     *
     * @return
     */
    public LoginBean readUserInfo() {
        if (aCache != null && isLogin()) {
            return (LoginBean) aCache.getAsObject("userInfo");
        }
        return null;
    }


    /**
     * 注册监听
     *
     * @param userInfoStatusListener
     */
    public void registerUserInfoStatusListener(UserInfoStatusListener userInfoStatusListener) {
        if (!userInfoStatusListeners.contains(userInfoStatusListener)) {
            userInfoStatusListeners.add(userInfoStatusListener);
        }
    }


    /**
     * 注销监听
     *
     * @param userInfoStatusListener
     */
    public void unRegisterUserInfoStatusListener(UserInfoStatusListener userInfoStatusListener) {
        if (userInfoStatusListeners.contains(userInfoStatusListener)) {
            userInfoStatusListeners.remove(userInfoStatusListener);
        }
    }

    /**
     * 定义用户状态接口
     */
    public interface UserInfoStatusListener {
        void onUserStatus(boolean isLogin, LoginBean userInfo);//用户登录状态
    }
}
