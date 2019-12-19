package com.example.shoppingcart.pay;

import java.util.Map;

public class PayMessage {
    private Map<String, String> result;//第一个参数，存放支付宝返回的结果信息
    private String outTradeNo;//第二个参数是服务端生成订单号

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
