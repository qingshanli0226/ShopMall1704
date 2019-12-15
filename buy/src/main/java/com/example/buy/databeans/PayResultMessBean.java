package com.example.buy.databeans;
/**
 * 客户端确认支付
 * */
public class PayResultMessBean {
    @Override
    public String toString() {
        return "PayResultMessBean{" +
                "alipay_trade_app_pay_response=" + alipay_trade_app_pay_response +
                ", sign='" + sign + '\'' +
                ", sign_type='" + sign_type + '\'' +
                '}';
    }

    /**
     * alipay_trade_app_pay_response : {"code":"10000","msg":"Success","app_id":"2016092200570833","auth_app_id":"2016092200570833","charset":"utf-8","timestamp":"2019-12-12 10:44:58","out_trade_no":"1212104358-6667","total_amount":"316.00","trade_no":"2019121222001458901000177650","seller_id":"2088102176778571"}
     * sign : uLbvTHhhSPqLnNduPQjHgLlD4jHnVtx0Pk2GvNoCtSLygZdIyQI4zZ1oFtqZVMIBhDwyBFCXB/t0foR+6p95APpsNu/hKwQb0uEUseRmTnDX3liXMuxOuKp3I262+QTE/ORsFYtruWPWIgFZqHHDIn4mQWvW1QRDJRCSnPhh4uV3BMtt0vS1pLYTNBGgU2/p6Te7UGniZCk9QevolYrwTmFrmmnLtfK8MxL3+SgDykm1F1jBc5r/0aORTY4oYO9ZoXX2SmQjbxUwIe58wFU76hWzAZzyIEkQX4mvLrAlE4G57FEah4FoxjT+G03G0luROlcHD99A7GIIcom2Qi3zbA==
     * sign_type : RSA2
     */

    private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public static class AlipayTradeAppPayResponseBean {
        @Override
        public String toString() {
            return "AlipayTradeAppPayResponseBean{" +
                    "code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    ", app_id='" + app_id + '\'' +
                    ", auth_app_id='" + auth_app_id + '\'' +
                    ", charset='" + charset + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", out_trade_no='" + out_trade_no + '\'' +
                    ", total_amount='" + total_amount + '\'' +
                    ", trade_no='" + trade_no + '\'' +
                    ", seller_id='" + seller_id + '\'' +
                    '}';
        }

        /**
         * code : 10000
         * msg : Success
         * app_id : 2016092200570833
         * auth_app_id : 2016092200570833
         * charset : utf-8
         * timestamp : 2019-12-12 10:44:58
         * out_trade_no : 1212104358-6667
         * total_amount : 316.00
         * trade_no : 2019121222001458901000177650
         * seller_id : 2088102176778571
         */

        private String code;
        private String msg;
        private String app_id;
        private String auth_app_id;
        private String charset;
        private String timestamp;
        private String out_trade_no;
        private String total_amount;
        private String trade_no;
        private String seller_id;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getAuth_app_id() {
            return auth_app_id;
        }

        public void setAuth_app_id(String auth_app_id) {
            this.auth_app_id = auth_app_id;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }
    }
}
