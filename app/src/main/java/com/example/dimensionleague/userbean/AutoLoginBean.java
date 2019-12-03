package com.example.dimensionleague.userbean;

public class AutoLoginBean {
    /**
     * code : 200
     * message : 登录成功
     * result : {"id":"1610","name":"1610","password":"1610","email":null,"phone":null,"point":null,"address":null,"money":null,"avatar":null,"token":"eaacae51-1c55-4ead-a31d-8070e336bc51AND1558449232809"}
     */

    public String code;
    public String message;
    public ResultBean result;

    public static class ResultBean {
        /**
         * id : 1610
         * name : 1610
         * password : 1610
         * email : null
         * phone : null
         * point : null
         * address : null
         * money : null
         * avatar : null
         * token : eaacae51-1c55-4ead-a31d-8070e336bc51AND1558449232809
         */

        public String id;
        public String name;
        public String password;
        public Object email;
        public Object phone;
        public Object point;
        public Object address;
        public Object money;
        public Object avatar;
        public String token;
    }
}
