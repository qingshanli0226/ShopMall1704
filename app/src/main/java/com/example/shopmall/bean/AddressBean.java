package com.example.shopmall.bean;

public class AddressBean {

    /**
     * code:200
     * message:请求成功
     * result:13433333333
     */

    private String code;
    private String message;
    private String result;

    public AddressBean() {
    }

    public AddressBean(String code, String message, String result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

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

    @Override
    public String toString() {
        return "AddressBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
