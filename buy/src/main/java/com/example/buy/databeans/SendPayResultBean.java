package com.example.buy.databeans;
/**
 * 支付确认
 * */
public class SendPayResultBean {
    @Override
    public String toString() {
        return "SendPayResultBean{" +
                "outTradeNo='" + outTradeNo + '\'' +
                ", result='" + result + '\'' +
                ", clientPayResult=" + clientPayResult +
                '}';
    }

    public SendPayResultBean(String outTradeNo, String result, boolean clientPayResult) {
        this.outTradeNo = outTradeNo;
        this.result = result;
        this.clientPayResult = clientPayResult;
    }

    /**
     * outTradeNo : 112810091743722
     * result : {"alipay_trade_app_pay_response":{"code":"10000","msg":"Success","app_id":"2016092200570833","auth_app_id":"2016092200570833","charset":"utf-8","timestamp":"2019-11-28 10:13:39","out_trade_no":"112810091743722","total_amount":"500.00","trade_no":"2019112822001427891000068428","seller_id":"2088102176778571"},"sign":"hDLn0JJ3M2DBu6i9aQCzw7W2SZ+gldu1Y2qDfOMXEdgj81vfVfXuCqOaX25aAi+StMuGfVb3Y5W495LaKPo5ItYo+HerhIBVFfAIyvNzC6XTLXGu7XEWXZumjMhT8EQbIRWFk76f4okzaqFNRqsE1\/VN8IwPVtEIjIpMBE\/YXBlQvk0k+uP9M6KNlli4UpaBdYu1GeNrd282X+IgDgMJje39yhS2blrvKrsT+pNuhyMsKX3RJlnoMYVjujEYgQZst5fs\/9zunNXC\/w0axcOIaePYU5eY7kQ7kuFcSy6JEeoycrA9o7hBWQZVKir8mDiMnwWKsE+rn9Ue4XNNSsCDdA==","sign_type":"RSA2"}
     * clientPayResult : true
     */

    private String outTradeNo;
    private String result;
    private boolean clientPayResult;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isClientPayResult() {
        return clientPayResult;
    }

    public void setClientPayResult(boolean clientPayResult) {
        this.clientPayResult = clientPayResult;
    }
}
