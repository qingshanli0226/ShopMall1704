package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StepBean {
    @Id(autoincrement = true)
    private Long id; //必须使用Long类型，long 或者 Integer int都不行
    @NotNull
    private String curr_date;

    @NotNull
    private int step;

    @Generated(hash = 2004512166)
    public StepBean(Long id, @NotNull String curr_date, int step) {
        this.id = id;
        this.curr_date = curr_date;
        this.step = step;
    }

    @Generated(hash = 781306117)
    public StepBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurr_date() {
        return this.curr_date;
    }

    public void setCurr_date(String curr_date) {
        this.curr_date = curr_date;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

}
