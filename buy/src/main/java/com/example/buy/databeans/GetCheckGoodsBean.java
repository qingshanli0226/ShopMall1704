package com.example.buy.databeans;
/**
 * 获取到的多个库存
 * */
    public class GetCheckGoodsBean {
    @Override
    public String toString() {
        return "GetCheckGoodsBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    /**
     * code : 200
     * message : æ£€æŸĄĺş“ĺ-ďĽŚćś‰å•†å“æ•°æ®æ•°é‡æ— æ³•æ»¡è¶³
     * result : [{"productNum":"2","productId":"1003","productName":"衬衫","url":"http://www.baidu.com"},{"productNum":"2","productId":"1004","productName":"衬衫","url":"http://www.baidu.com"}]
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
