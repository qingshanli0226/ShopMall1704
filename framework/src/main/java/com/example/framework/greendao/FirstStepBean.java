package com.example.framework.greendao;

import com.litesuits.orm.db.annotation.Column;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FirstStepBean {
    @Id(autoincrement = true)
    private Long id;
    @Column("first")
    int first;

    @Generated(hash = 13759971)
    public FirstStepBean(Long id, int first) {
        this.id = id;
        this.first = first;
    }

    @Generated(hash = 1054355734)
    public FirstStepBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFirst() {
        return this.first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    @Override
    public String toString() {
        return "FirstStepBean{" +
                "id=" + id +
                ", first=" + first +
                '}';
    }
}
