package com.example.shopmall.handler;

public class BigTask {

    //任务完成标记
    private int finishFlag = 0;

    private LittleInt littleInt;
    private LittleCache littleCache;

    public LittleInt getLittleInt() {
        return littleInt;
    }

    public void setLittleInt(LittleInt littleInt) {
        this.littleInt = littleInt;
    }

    public LittleCache getLittleCache() {
        return littleCache;
    }

    public void setLittleCache(LittleCache littleCache) {
        this.littleCache = littleCache;
    }

    public int getFinishFlag() {
        synchronized (BigTask.class){
            return finishFlag;
        }
    }

    public void incFinishFlag(){
        synchronized (BigTask.class){
            this.finishFlag++;
        }
    }

}
