package com.example.buy.databeans;

public class WaitPayBean {
    @Override
    public String  toString() {
        return "WaitPayBean{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", reserveOne=" + reserveOne +
                '}';
    }

    /**
     * subject : buy
     * body : 测试数据
     * totalPrice : 500
     * time : 1558940014208
     * status : null
     * reserveOne : null
     */

    private String subject;
    private String body;
    private String totalPrice;
    private String time;
    private Object status;
    private Object reserveOne;

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

    public Object getReserveOne() {
        return reserveOne;
    }

    public void setReserveOne(Object reserveOne) {
        this.reserveOne = reserveOne;
    }
}
