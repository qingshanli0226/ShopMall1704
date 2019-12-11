package com.example.buy.bean;

import java.util.List;

public class ShoppingCartBean {


    /**
     * code : 200
     * message : 请求成功
     * result : [{"productId":"1512","productName":"衬衫","productNum":"2","url":"http://www.baidu.com","productPrice":"20"}]
     */

    private String code;
    private String message;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * productId : 1512
         * productName : 衬衫
         * productNum : 2
         * url : http://www.baidu.com
         * productPrice : 20
         */

        private String productId;
        private String productName;
        private String productNum;
        private String url;
        private String productPrice;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNum() {
            return productNum;
        }

        public void setProductNum(String productNum) {
            this.productNum = productNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }
    }
}
