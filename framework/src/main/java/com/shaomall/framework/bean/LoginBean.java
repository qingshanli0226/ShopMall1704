package com.shaomall.framework.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {

    /**
     * id : 1610
     * name : 1610
     * password : 1610
     * email : null
     * phone : null
     * point : null
     * address : null
     * money : null
     * avatar : null
     * token : eaacae51-1c55-4ead-a31d-8070e336bc51AND1558449232809
     */

    private String id;
    private String name;
    private String password;
    private Object email;
    private Object phone;
    private Object point;
    private Object address;
    private Object money;
    private Object avatar;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getPoint() {
        return point;
    }

    public void setPoint(Object point) {
        this.point = point;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getMoney() {
        return money;
    }

    public void setMoney(Object money) {
        this.money = money;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
