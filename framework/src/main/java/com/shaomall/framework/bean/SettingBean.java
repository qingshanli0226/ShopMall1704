package com.shaomall.framework.bean;

public class SettingBean {
    private String Tittle;
    private String message;

    public SettingBean(String tittle, String message) {
        Tittle = tittle;
        this.message = message;
    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
