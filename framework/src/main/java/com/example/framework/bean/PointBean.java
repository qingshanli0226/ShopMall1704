package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PointBean {
    @Id(autoincrement = true)
    private Long id; //必须使用Long类型，long 或者 Integer int都不行
    @NotNull
    private String curr_date;

    @NotNull
    private String buy_title;

    @NotNull
    private int point;//消耗积分

    @Generated(hash = 2140171014)
    public PointBean(Long id, @NotNull String curr_date, @NotNull String buy_title,
            int point) {
        this.id = id;
        this.curr_date = curr_date;
        this.buy_title = buy_title;
        this.point = point;
    }

    @Generated(hash = 1572755726)
    public PointBean() {
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

    public String getBuy_title() {
        return this.buy_title;
    }

    public void setBuy_title(String buy_title) {
        this.buy_title = buy_title;
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
