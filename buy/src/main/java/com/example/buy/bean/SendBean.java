package com.example.buy.bean;

public class SendBean {


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

    @Override
    public String toString() {
        return "SendBean{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", tradeNo='" + tradeNo + '\'' +
                '}';
    }
}
