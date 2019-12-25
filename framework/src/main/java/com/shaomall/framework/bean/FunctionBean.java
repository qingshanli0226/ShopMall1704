package com.shaomall.framework.bean;

public class FunctionBean {

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
        return "FunctionBean{" +
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
