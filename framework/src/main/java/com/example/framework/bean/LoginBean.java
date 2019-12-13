package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


public class LoginBean {
    /**
     * code : 200
     * message : 登录成功
     * result : {"id":"1610","name":"1610","password":"1610","email":null,"phone":null,"point":null,"address":null,"money":null,"avatar":null,"token":"eaacae51-1c55-4ead-a31d-8070e336bc51AND1558449232809"}
     */

    private String code;
    private String message;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

}
