package com.example.shopmall.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StepBean {
    @Id
    private String data;//时间
    private String currentStrp;//当前步‍数
    private String afterStep;//之前存储步数
    @Generated(hash = 821183944)
    public StepBean(String data, String currentStrp, String afterStep) {
        this.data = data;
        this.currentStrp = currentStrp;
        this.afterStep = afterStep;
    }
    @Generated(hash = 781306117)
    public StepBean() {
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getCurrentStrp() {
        return this.currentStrp;
    }
    public void setCurrentStrp(String currentStrp) {
        this.currentStrp = currentStrp;
    }
    public String getAfterStep() {
        return this.afterStep;
    }
    public void setAfterStep(String afterStep) {
        this.afterStep = afterStep;
    }

    

}
