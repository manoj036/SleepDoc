package com.example.manojkumar.practiceui.firebase;

/**
 * Created by mbreath on 16/12/17.
 */

public class SleepData {
    long sleep_time, woke_up_time, time_took, total_sleep, out_of_bed_time, snore_time;
    int number_of_woke_up;
    int number_of_apnea_epoch;
    int sleep_score;
    int sleep_score_better_than;

    int max_resp, min_resp, avg_resp, var_resp, max_heart, min_heart, avg_heart, var_heart;
    int max_temp, min_temp, avg_temp, max_humidity, min_humidity, avg_humidity;
    int max_aq, min_aq, avg_aq, max_light, min_light, avg_light, max_noise, min_noise, avg_noise;

    public SleepData(long sleep_time, long woke_up_time, long time_took, long total_sleep, long out_of_bed_time, long snore_time, int number_of_woke_up, int number_of_apnea_epoch, int sleep_score, int sleep_score_better_than, int max_resp, int min_resp, int avg_resp, int var_resp, int max_heart, int min_heart, int avg_heart, int var_heart, int max_temp, int min_temp, int avg_temp, int max_humidity, int min_humidity, int avg_humidity, int max_aq, int min_aq, int avg_aq, int max_light, int min_light, int avg_light, int max_noise, int min_noise, int avg_noise) {
        this.sleep_time = sleep_time;
        this.woke_up_time = woke_up_time;
        this.time_took = time_took;
        this.total_sleep = total_sleep;
        this.out_of_bed_time = out_of_bed_time;
        this.snore_time = snore_time;
        this.number_of_woke_up = number_of_woke_up;
        this.number_of_apnea_epoch = number_of_apnea_epoch;
        this.sleep_score = sleep_score;
        this.sleep_score_better_than = sleep_score_better_than;
        this.max_resp = max_resp;
        this.min_resp = min_resp;
        this.avg_resp = avg_resp;
        this.var_resp = var_resp;
        this.max_heart = max_heart;
        this.min_heart = min_heart;
        this.avg_heart = avg_heart;
        this.var_heart = var_heart;
        this.max_temp = max_temp;
        this.min_temp = min_temp;
        this.avg_temp = avg_temp;
        this.max_humidity = max_humidity;
        this.min_humidity = min_humidity;
        this.avg_humidity = avg_humidity;
        this.max_aq = max_aq;
        this.min_aq = min_aq;
        this.avg_aq = avg_aq;
        this.max_light = max_light;
        this.min_light = min_light;
        this.avg_light = avg_light;
        this.max_noise = max_noise;
        this.min_noise = min_noise;
        this.avg_noise = avg_noise;
    }

    public int getSleep_score_better_than() {

        return sleep_score_better_than;
    }

    public void setSleep_score_better_than(int sleep_score_better_than) {
        this.sleep_score_better_than = sleep_score_better_than;
    }

    public SleepData() {
    }


    public long getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(long sleep_time) {
        this.sleep_time = sleep_time;
    }

    public long getWoke_up_time() {
        return woke_up_time;
    }

    public void setWoke_up_time(long woke_up_time) {
        this.woke_up_time = woke_up_time;
    }

    public long getTime_took() {
        return time_took;
    }

    public void setTime_took(long time_took) {
        this.time_took = time_took;
    }

    public long getTotal_sleep() {
        return total_sleep;
    }

    public void setTotal_sleep(long total_sleep) {
        this.total_sleep = total_sleep;
    }

    public long getOut_of_bed_time() {
        return out_of_bed_time;
    }

    public void setOut_of_bed_time(long out_of_bed_time) {
        this.out_of_bed_time = out_of_bed_time;
    }

    public long getSnore_time() {
        return snore_time;
    }

    public void setSnore_time(long snore_time) {
        this.snore_time = snore_time;
    }

    public int getNumber_of_woke_up() {
        return number_of_woke_up;
    }

    public void setNumber_of_woke_up(int number_of_woke_up) {
        this.number_of_woke_up = number_of_woke_up;
    }

    public int getNumber_of_apnea_epoch() {
        return number_of_apnea_epoch;
    }

    public void setNumber_of_apnea_epoch(int number_of_apnea_epoch) {
        this.number_of_apnea_epoch = number_of_apnea_epoch;
    }

    public int getSleep_score() {
        return sleep_score;
    }

    public void setSleep_score(int sleep_score) {
        this.sleep_score = sleep_score;
    }

    public int getMax_resp() {
        return max_resp;
    }

    public void setMax_resp(int max_resp) {
        this.max_resp = max_resp;
    }

    public int getMin_resp() {
        return min_resp;
    }

    public void setMin_resp(int min_resp) {
        this.min_resp = min_resp;
    }

    public int getAvg_resp() {
        return avg_resp;
    }

    public void setAvg_resp(int avg_resp) {
        this.avg_resp = avg_resp;
    }

    public int getVar_resp() {
        return var_resp;
    }

    public void setVar_resp(int var_resp) {
        this.var_resp = var_resp;
    }

    public int getMax_heart() {
        return max_heart;
    }

    public void setMax_heart(int max_heart) {
        this.max_heart = max_heart;
    }

    public int getMin_heart() {
        return min_heart;
    }

    public void setMin_heart(int min_heart) {
        this.min_heart = min_heart;
    }

    public int getAvg_heart() {
        return avg_heart;
    }

    public void setAvg_heart(int avg_heart) {
        this.avg_heart = avg_heart;
    }

    public int getVar_heart() {
        return var_heart;
    }

    public void setVar_heart(int var_heart) {
        this.var_heart = var_heart;
    }

    public int getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(int max_temp) {
        this.max_temp = max_temp;
    }

    public int getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(int min_temp) {
        this.min_temp = min_temp;
    }

    public int getAvg_temp() {
        return avg_temp;
    }

    public void setAvg_temp(int avg_temp) {
        this.avg_temp = avg_temp;
    }

    public int getMax_humidity() {
        return max_humidity;
    }

    public void setMax_humidity(int max_humidity) {
        this.max_humidity = max_humidity;
    }

    public int getMin_humidity() {
        return min_humidity;
    }

    public void setMin_humidity(int min_humidity) {
        this.min_humidity = min_humidity;
    }

    public int getAvg_humidity() {
        return avg_humidity;
    }

    public void setAvg_humidity(int avg_humidity) {
        this.avg_humidity = avg_humidity;
    }

    public int getMax_aq() {
        return max_aq;
    }

    public void setMax_aq(int max_aq) {
        this.max_aq = max_aq;
    }

    public int getMin_aq() {
        return min_aq;
    }

    public void setMin_aq(int min_aq) {
        this.min_aq = min_aq;
    }

    public int getAvg_aq() {
        return avg_aq;
    }

    public void setAvg_aq(int avg_aq) {
        this.avg_aq = avg_aq;
    }

    public int getMax_light() {
        return max_light;
    }

    public void setMax_light(int max_light) {
        this.max_light = max_light;
    }

    public int getMin_light() {
        return min_light;
    }

    public void setMin_light(int min_light) {
        this.min_light = min_light;
    }

    public int getAvg_light() {
        return avg_light;
    }

    public void setAvg_light(int avg_light) {
        this.avg_light = avg_light;
    }

    public int getMax_noise() {
        return max_noise;
    }

    public void setMax_noise(int max_noise) {
        this.max_noise = max_noise;
    }

    public int getMin_noise() {
        return min_noise;
    }

    public void setMin_noise(int min_noise) {
        this.min_noise = min_noise;
    }

    public int getAvg_noise() {
        return avg_noise;
    }

    public void setAvg_noise(int avg_noise) {
        this.avg_noise = avg_noise;
    }

    public long getSleepTime() {
        return sleep_time;
    }

    public void setSleepTime(long sleep_time) {
        this.sleep_time = sleep_time;
    }

    public long getWokeUpTime() {
        return woke_up_time;
    }

    public void setWokeUpTime(long woke_up_time) {
        this.woke_up_time = woke_up_time;
    }

    public long getTimeTook() {
        return time_took;
    }

    public void setTimeTook(long time_took) {
        this.time_took = time_took;
    }

    public long getTotalSleep() {
        return woke_up_time-sleep_time;
    }

    public void setTotalSleep(long total_sleep) {
        this.total_sleep = total_sleep;
    }

    public long getOutOfBedTime() {
        return out_of_bed_time;
    }

    public void setOutOfBedTime(long out_of_bed_time) {
        this.out_of_bed_time = out_of_bed_time;
    }

    public long getSnoreTime() {
        return snore_time;
    }

    public void setSnoreTime(long snore_time) {
        this.snore_time = snore_time;
    }

    public int getNumberOfWokeUp() {
        return number_of_woke_up;
    }

    public void setNumberOfWokeUp(int number_of_woke_up) {
        this.number_of_woke_up = number_of_woke_up;
    }

    public int getNumberOfApneaEpoch() {
        return number_of_apnea_epoch;
    }

    public void setNumberOfApneaEpoch(int number_of_apnea_epoch) {
        this.number_of_apnea_epoch = number_of_apnea_epoch;
    }

    public int getSleepScore() {
        return sleep_score;
    }

    public void setSleepScore(int sleep_score) {
        this.sleep_score = sleep_score;
    }

    @Override
    public String toString() {
        return "SleepData{" +
                "sleep_time=" + sleep_time +
                ", woke_up_time=" + woke_up_time +
                ", time_took=" + time_took +
                ", total_sleep=" + total_sleep +
                ", out_of_bed_time=" + out_of_bed_time +
                ", snore_time=" + snore_time +
                ", number_of_woke_up=" + number_of_woke_up +
                ", number_of_apnea_epoch=" + number_of_apnea_epoch +
                ", sleep_score=" + sleep_score +
                ", sleep_score_better_than=" + sleep_score_better_than +
                ", max_resp=" + max_resp +
                ", min_resp=" + min_resp +
                ", avg_resp=" + avg_resp +
                ", var_resp=" + var_resp +
                ", max_heart=" + max_heart +
                ", min_heart=" + min_heart +
                ", avg_heart=" + avg_heart +
                ", var_heart=" + var_heart +
                ", max_temp=" + max_temp +
                ", min_temp=" + min_temp +
                ", avg_temp=" + avg_temp +
                ", max_humidity=" + max_humidity +
                ", min_humidity=" + min_humidity +
                ", avg_humidity=" + avg_humidity +
                ", max_aq=" + max_aq +
                ", min_aq=" + min_aq +
                ", avg_aq=" + avg_aq +
                ", max_light=" + max_light +
                ", min_light=" + min_light +
                ", avg_light=" + avg_light +
                ", max_noise=" + max_noise +
                ", min_noise=" + min_noise +
                ", avg_noise=" + avg_noise +
                '}';
    }
}
