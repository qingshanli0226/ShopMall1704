package com.shaomall.framework.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.commen.ACache;
import com.example.commen.TokenUtil;
import com.example.commen.network.NetWorkUtils;
import com.example.commen.util.ErrorUtil;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.net.MVPObserver;
import com.example.net.ResEntity;
import com.example.net.RetrofitCreator;
import com.example.net.sign.SignUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shaomall.framework.bean.LoginBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UserInfoManager {
    @SuppressLint("StaticFieldLeak")
    private static UserInfoManager instance;
    private Context mContext;
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

        sharedPreferences = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        TokenUtil.getInstance().init(mContext); //初始化token工具类
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
            edit.remove("token");
            edit.clear();
            edit.commit();

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

        boolean b = aCacheSaveUserInfo(dataBean);
        if (b) {
            //登录状态监听
            for (UserInfoStatusListener listener : userInfoStatusListeners) {
                listener.onUserStatus(isLogin(), readUserInfo());
            }
        }
    }

    /**
     * 将用户信息保存到本地
     *
     * @param dataBean
     * @return
     */
    public boolean aCacheSaveUserInfo(LoginBean dataBean) {
        if (aCache != null) {
            aCache.put("userInfo", dataBean); //缓存用户信息
            //设置登录状态
            SharedPreferences.Editor edit = sharedPreferences.edit();
            //edit.putBoolean("isLogin", true);
            //通过token值, 判断是否登录
            edit.putString("token", dataBean.getToken());
            edit.apply();

            return true;
        }
        return false;
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


    /////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 自动登录
     */
    public void autoLogin() {
        //判断网络状态
        if (!NetWorkUtils.isNetWorkAvailable()) {
            return;
        }

        String token = getToken();
        if (token == null) {
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);

        Log.i("ssh", "autoLogin: " + getToken());

        unLogout();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //转化成treeMap
        TreeMap<String, String> emptyTreeMap = SignUtil.getEmptyTreeMap();
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            emptyTreeMap.put(entry.getKey(), entry.getValue());
        }

        //根据getParams()返回的参数, 生成对应的签名
        String sign = null;
        sign = SignUtil.generateSign(emptyTreeMap);
        hashMap.put("sign", sign);
        //进行加密, 利用TreeMap
        Map<String, String> encryptParamMap = SignUtil.encryptParamsByBase64(hashMap);


        RetrofitCreator.getNetApiService().postData(new HashMap<String, String>(), AppNetConfig.AUTO_LOGIN_URL, encryptParamMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MVPObserver<ResponseBody>() {

                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (!mDisposable.isDisposed()) {//未取消订阅
                            String toString;
                            try {
                                toString = responseBody.string();

                                ResEntity<LoginBean> resEntity = new Gson().fromJson(toString, new TypeToken<ResEntity<LoginBean>>() {
                                }.getType());
                                int code = resEntity.getCode();
                                if (code == ShopMailError.SUCCESS.getErrorCode()) {//请求成功
                                    aCacheSaveUserInfo(resEntity.getResult());


                                } else {
                                    Toast.makeText(mContext, "auto: " + ErrorUtil.dataProcessing(code).getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                mDisposable.dispose();//取消订阅
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG", "onError: " + e.getMessage());
                        Toast.makeText(mContext, ErrorUtil.handlerError(e).getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
