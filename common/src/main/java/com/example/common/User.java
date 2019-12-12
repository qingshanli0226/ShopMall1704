package com.example.common;

public class User {

    /**
     * id : lihaofan
     * name : lihaofan
     * password : lihaofan
     * email : null
     * phone : null
     * point : null
     * address : null
     * money : null
     * avatar : null
     * token : 7ac0b7b6-8653-488e-8260-59d9486a2502AND1575482985543
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

    public User(String name, String password, Object email, Object phone, Object point, Object address, Object money, Object avatar) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.point = point;
        this.address = address;
        this.money = money;
        this.avatar = avatar;
    }

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
