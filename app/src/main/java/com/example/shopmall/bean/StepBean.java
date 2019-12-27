package com.example.shopmall.bean;


class StepBean {

    private String data;//时间
    private String currentStrp;//当前步‍数
    private String afterStep;//之前存储步数

    public StepBean(String data, String currentStrp, String afterStep) {
        this.data = data;
        this.currentStrp = currentStrp;
        this.afterStep = afterStep;
    }

    public StepBean() {
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCurrentStrp() {
        return this.currentStrp;
    }

    public void setCurrentStrp(String currentStrp) {
        this.currentStrp = currentStrp;
    }

    public String getAfterStep() {
        return this.afterStep;
    }

    public void setAfterStep(String afterStep) {
        this.afterStep = afterStep;
    }


}
