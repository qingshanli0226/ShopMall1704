package com.example.buy.databeans;

public class CartBean {
    @Override
    public String toString() {
        return "CartBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    /**
     * code : 200
     * message : 请求成功
     * result : [{"productId":"1512","productNum":1,"productName":"衬衫","url":"http://www.baidu.com"}]
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
