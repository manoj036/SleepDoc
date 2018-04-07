package com.example.manojkumar.practiceui.firebase;

/**
 * Created by mbreath on 16/12/17.
 */

public class EnvironmentalData {
    int temp;
    int aq;
    int humidity;

    public EnvironmentalData() {
    }

    public EnvironmentalData(int temp, int aq, int humidity) {
        this.temp = temp;
        this.aq = aq;
        this.humidity = humidity;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getAq() {
        return aq;
    }

    public void setAq(int aq) {
        this.aq = aq;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
