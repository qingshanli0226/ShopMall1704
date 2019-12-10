package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    @Id
    private Long id;
    private String imgsrc;
    private String name;
    private String password;
    private int phone;
    private String integral;//积分

    @Generated(hash = 1465473956)
    public UserBean(Long id, String imgsrc, String name, String password, int phone,
                    String integral) {
        this.id = id;
        this.imgsrc = imgsrc;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.integral = integral;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgsrc() {
        return this.imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return this.phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getIntegral() {
        return this.integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }


}
