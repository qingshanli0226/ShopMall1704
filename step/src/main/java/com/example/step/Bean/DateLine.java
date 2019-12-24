package com.example.step.Bean;

public class DateLine {

    String dateTime;
    int  currents;


    public DateLine(String dateTime, int currents) {
        this.dateTime = dateTime;
        this.currents = currents;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getCurrents() {
        return currents;
    }

    public void setCurrents(int currents) {
        this.currents = currents;
    }

    @Override
    public String toString() {
        return "DateLine{" +
                "dateTime='" + dateTime + '\'' +
                ", currents=" + currents +
                '}';
    }
}
