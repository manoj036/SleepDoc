package com.example.manojkumar.practiceui.model;

import com.example.manojkumar.practiceui.firebase.VitalsData;

/**
 * Created by mbreath on 05/04/18.
 */

public class KeyVitalsData {
    private int time;
    private VitalsData mVitalsData;

    public KeyVitalsData() {
    }

    public KeyVitalsData(int time, VitalsData vitalsData) {
        this.time = time;
        mVitalsData = vitalsData;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public VitalsData getVitalsData() {
        return mVitalsData;
    }

    public void setVitalsData(VitalsData vitalsData) {
        mVitalsData = vitalsData;
    }
}
