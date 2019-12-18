package com.example.framework.bean;

public class MessageStepBean {


    String time;
    String date;
    int currentStep;
    int integral;

    public MessageStepBean(String time, String date, int currentStep, int integral) {
        this.time = time;
        this.date = date;
        this.currentStep = currentStep;
        this.integral = integral;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "MessageStepBean{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", currentStep=" + currentStep +
                ", integral=" + integral +
                '}';
    }

}
