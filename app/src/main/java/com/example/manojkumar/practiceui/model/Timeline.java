package com.example.manojkumar.practiceui.model;

/**
 * Created by mbreath on 01/04/18.
 */

public class Timeline {
    public String date, timelineText, temp, humidity, light, noise, aq;

    public Timeline(String date, String temp, String humidity, String light, String noise, String aq) {
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.light = light;
        this.noise = noise;
        this.aq = aq;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getAq() {
        return aq;
    }

    public void setAq(String aq) {
        this.aq = aq;
    }

    public Timeline(String date, String status) {
        this.date = date;
        this.timelineText = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimelineText() {
        return timelineText;
    }

    public void setTimelineText(String timelineText) {
        this.timelineText = timelineText;
    }
}
