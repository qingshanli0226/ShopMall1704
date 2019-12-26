package com.example.buy.bean;

import java.io.Serializable;
import java.util.List;

public class PayGoodsBean implements Serializable {

    /**
     * code : 200
     * message : 请求成功
     * result : [{"subject":"buy","body":"测试数据","totalPrice":"500","time":"1576032465028","status":null,"tradeNo":"121110474516759","orderInfo":null},{"subject":"buy","body":"测试数据","totalPrice":"500","time":"1576121448795","status":null,"tradeNo":"121211304851664","orderInfo":null},{"subject":"buy","body":"测试数据","totalPrice":"500","time":"1576122484560","status":null,"tradeNo":"1212114804-1795","orderInfo":null},{"subject":"buy","body":"测试数据","totalPrice":"500","time":"1577190346671","status":null,"tradeNo":"1224202546-4415","orderInfo":"app_id=2016092200570833&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%22500%22%2C%22subject%22%3A%22buy%22%2C%22body%22%3A%22%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%221224202546-4415%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2019-12-24+20%3A25%3A46&version=1.0&sign=E7MqP7APa54zSpsuuOF2xmdBTh%2F7FTyuGta6xdpqCfWyEj19qulFQ4%2FUDqHRLpJJvq6tsgJ4%2FosGj%2BtsSfGHOV4PQT8IbYHejuLkx3epUDcTa9vefrxF8LT6o1ParncubKvLVkwpR5RLeQUs6kSPyS9xXgYBgkHHFNyYVJD38g%2B6v93F8ScJMUBC1Mc3ZTwRXyvyYgTQAiY2gK9NvNFRd97Usa828D7gdD7N2SGJUJJ7VbzNRUKEKZ7QFYRlHrudfGYNypNv4DsI2k0RGOUe%2B7m759euZhAF9ziJR9dU1CXjyoKbFvEGG%2F%2FRQ9rLW6uEadJEg%2FpkgFrra34biSAa0g%3D%3D"},{"subject":"buy","body":"测试数据","totalPrice":"500","time":"1577190452201","status":null,"tradeNo":"122420273278784","orderInfo":"app_id=2016092200570833&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%22500%22%2C%22subject%22%3A%22buy%22%2C%22body%22%3A%22%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22122420273278784%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2019-12-24+20%3A27%3A32&version=1.0&sign=ghdv4CNiBD%2BtqixB7%2BKqMQ%2Byya5Wo0mR9t6mDS6YO4yZE4vVBwpmeugD%2FRHyK5p%2FcDA5eZ8jYZa4SV6MItBUoD%2Flsdieju2WrErLV2ScfcRbsrMLJS5hHQ0e%2BNqLMgNT%2FSfF7%2FpKgLdkBWcDnQTjxBLLrpdcdpN5KbFFGH16h%2BpLqi4FdF9aTi3IUKLHqVUztlrR%2ByFHrkrLXy%2F8si7bEj%2FSPeshE83ZyCbcFcErnnDHKwK8OV1WB9t6tlnxXs7Gox1PyYuVi0TYfSHWH2XEqgdL%2Bz8EfOogzF%2FXhWNVn6EfO%2FieRppzARJE23n17hEvmjx2GumHnMxVDlcOphHhWw%3D%3D"}]
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

    public static class ResultBean implements Serializable{
        /**
         * subject : buy
         * body : 测试数据
         * totalPrice : 500
         * time : 1576032465028
         * status : null
         * tradeNo : 121110474516759
         * orderInfo : null
         */

        private String subject;
        private String body;
        private String totalPrice;
        private String time;
        private Object status;
        private String tradeNo;
        private Object orderInfo;

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

        public Object getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(Object orderInfo) {
            this.orderInfo = orderInfo;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "subject='" + subject + '\'' +
                    ", body='" + body + '\'' +
                    ", totalPrice='" + totalPrice + '\'' +
                    ", time='" + time + '\'' +
                    ", status=" + status +
                    ", tradeNo='" + tradeNo + '\'' +
                    ", orderInfo=" + orderInfo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PayGoodsBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
