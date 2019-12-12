package com.shaomall.framework.manager;

/**
 * 积分管理类
 */
public class PointManager {
    private static PointManager instance = null;
    private CallbackIntegralListener callbackIntegralListener = null;


    private PointManager() {
    }

    public static PointManager getInstance() {
        if (instance == null) {
            synchronized (PointManager.class) {
                if (instance == null) {
                    instance = new PointManager();
                }
            }
        }
        return instance;
    }

    public void setPointNum(int pointNum){
        this.callbackIntegralListener.onCallbacksIntegral(pointNum);
    }


    /**
     * 注册监听
     *
     * @param callbackIntegralListener
     */
    public void registerCallbackIntegralListener(CallbackIntegralListener callbackIntegralListener) {
        this.callbackIntegralListener = callbackIntegralListener;
    }

    /**
     * 注销监听
     *
     * @param callbackIntegralListener
     */
    public void unRegisterCallbackIntegralListener(CallbackIntegralListener callbackIntegralListener) {
        if (callbackIntegralListener != null) {
            this.callbackIntegralListener = null;
        }
    }

    public interface CallbackIntegralListener {
        void onCallbacksIntegral(int pointNum);
    }
}
