package com.example.buy.databeans;

public class StepBean {
    private String CURR_date;
    private int step;

    public StepBean(String CURR_date, int step) {
        this.CURR_date = CURR_date;
        this.step = step;
    }

    @Override
    public String toString() {
        return "StepBean{" +
                "CURR_date='" + CURR_date + '\'' +
                ", step=" + step +
                '}';
    }

    public String getCURR_date() {
        return CURR_date;
    }

    public void setCURR_date(String CURR_date) {
        this.CURR_date = CURR_date;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}

