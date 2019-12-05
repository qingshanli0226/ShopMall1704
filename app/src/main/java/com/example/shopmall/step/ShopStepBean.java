package com.example.shopmall.step;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

@Table("step")
public class ShopStepBean {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    int id;
    @Column("day")
    String date;
    @Column("current")
    String current_step;
    @Column("yesCurrent")
    String yesCurrent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrent_step() {
        return current_step;
    }

    public void setCurrent_step(String current_step) {
        this.current_step = current_step;
    }

    public String getYesCurrent() {
        return yesCurrent;
    }

    public void setYesCurrent(String yesCurrent) {
        this.yesCurrent = yesCurrent;
    }

    @Override
    public String toString() {
        return "ShopStepBean{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", current_step='" + current_step + '\'' +
                ", yesCurrent='" + yesCurrent + '\'' +
                '}';
    }
}

