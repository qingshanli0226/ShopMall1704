package com.example.buy.databeans;

import java.util.List;

public class GetCartBean {
    @Override
    public String toString() {
        return "GetCartBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * code : 200
     * message : 请求成功
     * result : [{"productId":"1512","productName":"衬衫","productNum":"2","url":"http://www.baidu.com","productPrice":"20"}]
     */

    private String code;
    private String message;
    private List<GoodsBean> result;

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

    public List<GoodsBean> getResult() {
        return result;
    }

    public void setResult(List<GoodsBean> result) {
        this.result = result;
    }

}
