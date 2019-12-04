package com.example.remindsteporgan.Base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Bean {

    private Long num;
    private String time;
    @Generated(hash = 1513477921)
    public Bean(Long num, String time) {
        this.num = num;
        this.time = time;
    }
    @Generated(hash = 80546095)
    public Bean() {
    }
    public Long getNum() {
        return this.num;
    }
    public void setNum(Long num) {
        this.num = num;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
