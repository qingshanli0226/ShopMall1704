package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShopStepTimeRealBean {

    @Id(autoincrement = true)
    private Long id;

    private String time;
    private String date;
    private int currentStep;
    @Generated(hash = 1073878023)
    public ShopStepTimeRealBean(Long id, String time, String date,
            int currentStep) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.currentStep = currentStep;
    }
    @Generated(hash = 1496948093)
    public ShopStepTimeRealBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getCurrentStep() {
        return this.currentStep;
    }
    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    @Override
    public String toString() {
        return "ShopStepTimeRealBean{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", currentStep=" + currentStep +
                '}';
    }
}
