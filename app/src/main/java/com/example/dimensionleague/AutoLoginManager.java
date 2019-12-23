package com.example.dimensionleague;

import com.example.common.code.Constant;
import com.example.common.utils.SPUtil;
import com.example.common.utils.SignUtil;
import com.example.dimensionleague.userbean.AutoLoginBean;
import com.example.framework.manager.ErrorDisposeManager;
import com.example.net.AppNetConfig;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author:李浩帆
 */
public class AutoLoginManager {
    //TODO 自动登录成功的监听
    private IAutoLoginReceivedListener loginReceivedListener;
    //TODO 单例模式
    private static AutoLoginManager autoLoginManager;
    private AutoLoginManager() {

    }
    public static AutoLoginManager getInstance() {
        if (autoLoginManager == null) {
            autoLoginManager = new AutoLoginManager();
        }
        return autoLoginManager;
    }
    //TODO 注册监听
    public void registerAutoLoginListener(IAutoLoginReceivedListener loginReceivedListener) {
        this.loginReceivedListener = loginReceivedListener;
    }
    //TODO 注销监听
    public void unRegisterAutoLoginListener() {
        if (loginReceivedListener != null)
            loginReceivedListener = null;
    }
    public void getLoginData() {
        //TODO 创建一个hashMap来存储token
        TreeMap<String, String> treeMap = SignUtil.getEmptyTreeMap();
        treeMap.put(Constant.TOKEN, SPUtil.getToken());
        //TODO 加签
        String sign = SignUtil.generateSign(treeMap);
        treeMap.put("sign", sign);
        //TODO 加密
        TreeMap<String, String> map = SignUtil.encryptParamsByBase64(treeMap);
        RetrofitCreator.getNetInterence().postData(new HashMap<>(), AppNetConfig.AUTOLOGIN, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            AutoLoginBean autoLoginBean = new Gson().fromJson(string, AutoLoginBean.class);
                            loginReceivedListener.onAutoLoginReceived(autoLoginBean.getResult());
                        } catch (IOException ignored) {
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e!=null){
                            loginReceivedListener.onAutoDataError(e.getMessage());
                            ErrorDisposeManager.HandlerError(e);
                        }
                    }
                    @Override
                    public void onComplete() {}
                });
    }
    public interface IAutoLoginReceivedListener {
        void onAutoLoginReceived(AutoLoginBean.ResultBean resultBean);
        void onAutoDataError(String s);
    }
}
