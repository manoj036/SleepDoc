package com.example.manojkumar.practiceui.firebase;

/**
 * Created by mbreath on 16/12/17.
 */

public class VitalsData {
    int resp_rate, resp_var, heart_rate, heart_var, body_movement, light, noise, sleep_stage;
    boolean is_apnea, is_snoring;

    public VitalsData() {
    }

    public VitalsData(int resp_rate, int resp_var, int heart_rate,
                      int heart_var, int bodyMovement, int light, int noise,
                      int sleep_stage, boolean is_apnea, boolean is_snoring) {
        this.resp_rate = resp_rate;
        this.resp_var = resp_var;
        this.heart_rate = heart_rate;
        this.heart_var = heart_var;
        this.body_movement = body_movement;
        this.light = light;
        this.noise = noise;
        this.sleep_stage = sleep_stage;
        this.is_apnea = is_apnea;
        this.is_snoring = is_snoring;
    }

    public int getResp_rate() {
        return resp_rate;
    }

    public void setResp_rate(int resp_rate) {
        this.resp_rate = resp_rate;
    }

    public int getResp_var() {
        return resp_var;
    }

    public void setResp_var(int resp_var) {
        this.resp_var = resp_var;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getHeart_var() {
        return heart_var;
    }

    public void setHeart_var(int heart_var) {
        this.heart_var = heart_var;
    }

    public int getBody_movement() {
        return body_movement;
    }

    public void setBody_movement(int body_movement) {
        this.body_movement = body_movement;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getNoise() {
        return noise;
    }

    public void setNoise(int noise) {
        this.noise = noise;
    }

    public int getSleep_stage() {
        return sleep_stage;
    }

    public void setSleep_stage(int sleep_stage) {
        this.sleep_stage = sleep_stage;
    }

    public boolean isIs_apnea() {
        return is_apnea;
    }

    public void setIs_apnea(boolean is_apnea) {
        this.is_apnea = is_apnea;
    }

    public boolean isIs_snoring() {
        return is_snoring;
    }

    public void setIs_snoring(boolean is_snoring) {
        this.is_snoring = is_snoring;
    }

    @Override
    public String toString() {
        return "Vitals{" +
                "respRate=" + resp_rate +
                ", respVar=" + resp_var +
                ", heartRate=" + heart_rate +
                ", heartVar=" + heart_var +
                ", bodyMovement=" + body_movement +
                ", light=" + light +
                ", noise=" + noise +
                ", sleepStage=" + sleep_stage +
                ", isApnea=" + is_apnea +
                ", isSnoring=" + is_snoring +
                '}';
    }
}
