package com.example.buy.databeans;

import java.util.List;

/**
 * 获取到的下订单
 * */
public class GetOrderBean {
    @Override
    public String toString() {
        return "GetOrderBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * code : 200
     * message : 请求成功
     * result : [{"subject":"??","body":"????","totalPrice":"600","time":"1576296108129","status":null,"tradeNo":"121412014813510"}]
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
        @Override
        public String toString() {
            return "ResultBean{" +
                    "subject='" + subject + '\'' +
                    ", body='" + body + '\'' +
                    ", totalPrice='" + totalPrice + '\'' +
                    ", time='" + time + '\'' +
                    ", status=" + status +
                    ", tradeNo='" + tradeNo + '\'' +
                    '}';
        }

        /**
         * subject : ??
         * body : ????
         * totalPrice : 600
         * time : 1576296108129
         * status : null
         * tradeNo : 121412014813510
         */

        private String subject;
        private String body;
        private String totalPrice;
        private String time;
        private Object status;
        private String tradeNo;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }
    }
}
