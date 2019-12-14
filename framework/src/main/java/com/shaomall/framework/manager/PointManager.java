package com.shaomall.framework.manager;

public class PointManager {
    private static PointManager instance;
    private CallbackIntegralListener callbackIntegralListener = null;
    private long oldPoint = 0;

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

    public void setPointNum(int pointNum) {
        if (oldPoint != pointNum) {
            callbackIntegralListener.onCallbacksIntegral(pointNum);
        }
        oldPoint = pointNum;
    }


    public void registerCallbackIntegralListener(CallbackIntegralListener callbackIntegralListener) {
        this.callbackIntegralListener = callbackIntegralListener;
    }

    public void unRegisterCallbackIntegralListener() {
        if (this.callbackIntegralListener != null) {
            this.callbackIntegralListener = null;
        }
    }


    public interface CallbackIntegralListener {
        void onCallbacksIntegral(int pointNum);
    }
}
