package com.shaomall.framework.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.commen.ACache;
import com.example.commen.TokenUtil;
import com.shaomall.framework.bean.LoginBean;

import java.util.LinkedList;

public class UserInfoManager {
    private Context mContext;
    private static UserInfoManager instance;
    private ACache aCache;
    private SharedPreferences sharedPreferences;
    private final LinkedList<UserInfoStatusListener> userInfoStatusListeners = new LinkedList<>();
    private LinkedList<AvatarUpdateListener> avatarUpdateListeners = new LinkedList<>();


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
        //        TokenUtil.sharedPreferences = sharedPreferences;


    }


    /**
     * 用户登录状态
     *
     * @return
     */
    public boolean isLogin() {
        //return sharedPreferences.getBoolean("isLogin", false);
        return getToken() != null;
    }

    /**
     * 获取token值
     *
     * @return
     */
    public String getToken() {
        return sharedPreferences.getString("token", null);
    }

    /**
     * 退出登录
     */
    public void unLogout() {
        if (isLogin()) { //登录状态
            aCache.remove("userInfo");
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear();
            edit.apply();

            if (TokenUtil.sharedPreferences != null) {
                TokenUtil.sharedPreferences = sharedPreferences;
            }

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
            //edit.putBoolean("isLogin", true);
            //通过token值, 判断是否登录
            edit.putString("token", dataBean.getToken());
            edit.apply();

            if (TokenUtil.sharedPreferences == null) {
                TokenUtil.sharedPreferences = sharedPreferences;
            }


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

    public LoginBean upDataAvater(String path) {
        LoginBean loginBean = readUserInfo();
        loginBean.setAvatar(path);
        aCache.put("userInfo", loginBean); //缓存用户信息
        notifyUserAvatarUpdate(path);
        return readUserInfo();
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
        userInfoStatusListeners.remove(userInfoStatusListener);
    }

    /**
     * 定义用户状态接口
     */
    public interface UserInfoStatusListener {
        void onUserStatus(boolean isLogin, LoginBean userInfo);//用户登录状态


    }


    /**
     * 注册监听
     *
     * @param avatarUpdateListener
     */
    public void registerAvatarUpdateListener(AvatarUpdateListener avatarUpdateListener) {
        if (!avatarUpdateListeners.contains(avatarUpdateListener)) {
            avatarUpdateListeners.add(avatarUpdateListener);
        }
    }


    /**
     * 注销监听
     *
     * @param avatarUpdateListener
     */
    public void unRegisterAvatarUpdateListener(AvatarUpdateListener avatarUpdateListener) {
        avatarUpdateListeners.remove(avatarUpdateListener);
    }

    public interface AvatarUpdateListener {
        void onAvatarUpdate(String path);
    }


    //TODO 更新头像后通知
    private void notifyUserAvatarUpdate(String path) {
        synchronized (userInfoStatusListeners) {
            for (AvatarUpdateListener updateListener : avatarUpdateListeners) {
                updateListener.onAvatarUpdate(path);
            }
        }
    }
}
