package com.example.framework.bean;

public class HourBean {
    String time;
    String date;
    int currentStep;

    public HourBean(String time, String date, int currentStep) {
        this.time = time;
        this.date = date;
        this.currentStep = currentStep;
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

    @Override
    public String toString() {
        return "HourBean{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", currentStep=" + currentStep +
                '}';
    }
}
