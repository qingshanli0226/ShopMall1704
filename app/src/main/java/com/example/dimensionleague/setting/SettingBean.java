package com.example.dimensionleague.setting;

public class SettingBean {
    private String Title;
    private String massage;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public SettingBean(String title, String massage) {
        Title = title;
        this.massage = massage;
    }
}
