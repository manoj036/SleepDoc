package com.example.manojkumar.practiceui.model;

import com.example.manojkumar.practiceui.firebase.EnvironmentalData;

/**
 * Created by mbreath on 07/04/18.
 */

public class KeyEnvironmentData {
    private int time;
    private EnvironmentalData mEnvironmentalData;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public EnvironmentalData getEnvironmentalData() {
        return mEnvironmentalData;
    }

    public void setEnvironmentalData(EnvironmentalData environmentalData) {
        mEnvironmentalData = environmentalData;
    }

}
