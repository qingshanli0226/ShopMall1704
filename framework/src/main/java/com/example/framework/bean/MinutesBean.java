package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MinutesBean {
    @Id(autoincrement = true)
    private Long id; //必须使用Long类型，long 或者 Integer int都不行
    @NotNull
    private String user;//用户名
    @NotNull
    private String minutes;

    @NotNull
    private int step;

    @Generated(hash = 726072389)
    public MinutesBean(Long id, @NotNull String user, @NotNull String minutes,
            int step) {
        this.id = id;
        this.user = user;
        this.minutes = minutes;
        this.step = step;
    }

    @Generated(hash = 1672037621)
    public MinutesBean() {
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

    public String getMinutes() {
        return this.minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

}
