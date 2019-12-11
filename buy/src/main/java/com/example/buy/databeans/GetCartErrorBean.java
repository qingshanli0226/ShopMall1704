package com.example.buy.databeans;

import java.util.List;

public class GetCartErrorBean {
    @Override
    public String toString() {
        return "GetCartErrorBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * code : 1003
     * message : 获取购物车产品出现错误
     * result : []
     */

    private String code;
    private String message;
    private List<?> result;

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

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }
}
