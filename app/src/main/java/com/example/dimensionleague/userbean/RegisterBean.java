package com.example.dimensionleague.userbean;

public class RegisterBean {

    /**
     * code : 200
     * message : 请求成功
     * result : 注册成功
     */

    public String code;
    public String message;
    public String result;

    @Override
    public String toString() {
        return "RegisterBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
