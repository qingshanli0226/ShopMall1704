package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HourBean {
    @Id(autoincrement = true)
    private Long id; //必须使用Long类型，long 或者 Integer int都不行
    @NotNull
    private String user;//用户名
    @NotNull
    private String hours;

    @NotNull
    private int step;

    @Generated(hash = 1792365420)
    public HourBean(Long id, @NotNull String user, @NotNull String hours,
            int step) {
        this.id = id;
        this.user = user;
        this.hours = hours;
        this.step = step;
    }

    @Generated(hash = 1171223571)
    public HourBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHours() {
        return this.hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

}
