package com.example.buy.bean;

import androidx.annotation.NonNull;

public class InsertBean {

    /**
     * code : 200
     * message : 请求成功
     * result : 请求成功
     */

    private String code;
    private String message;
    private String result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @NonNull
    @Override
    public String toString() {
        return "InsertBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
