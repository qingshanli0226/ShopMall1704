package com.example.buy.databeans;

/**
 * 多种请求后返回的数据
 * */
public class OkBean {
    @Override
    public String toString() {
        return "OkBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    /**
     * code : 200
     * message : 请求成功
     * result : 6666666
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
}
