package com.example.step;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

@Table("gal")
public class Gal {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    int id;
    @Column("integral")
    int integral;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "Gal{" +
                "id=" + id +
                ", integral=" + integral +
                '}';
    }
}
