package com.example.manojkumar.practiceui.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by pathath on 21-Dec-16.
 */
public class DayData {

    private int sleep_score,number_of_woke_up,number_of_apnea_epoch;
    private Long sleep_time= 0L,woke_up_time= 0L,time_took= 0L,snore_time= 0L;
    private double total_sleep;

    public int getSleep_score() {
        return sleep_score;
    }

    public void setSleep_score(int sleep_score) {
        this.sleep_score = sleep_score;
    }

    public Long getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(Long sleep_time) {
        this.sleep_time = sleep_time;
    }

    public Long getWoke_up_time() {
        return woke_up_time;
    }

    public void setWoke_up_time(Long woke_up_time) {
        this.woke_up_time = woke_up_time;
    }

    public Long getTime_took() {
        return time_took;
    }

    public void setTime_took(Long time_took) {
        this.time_took = time_took;
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

    public Long getSnore_time() {
        return snore_time;
    }

    public void setSnore_time(Long snore_time) {
        this.snore_time = snore_time;
    }

    public double getTotal_sleep() {
        return total_sleep;
    }

    public void setTotal_sleep(double total_sleep) {
        this.total_sleep = total_sleep;
    }

    public static Date dateFromUTC(Date date){
        return new Date(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
    }

    public static Date dateToUTC(Date date){
        return new Date(date.getTime() - Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
    }
}