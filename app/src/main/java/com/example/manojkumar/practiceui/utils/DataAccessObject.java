package com.example.manojkumar.practiceui.utils;

import com.example.manojkumar.practiceui.model.DayData;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by pathath on 21-Dec-16.
 */
public class DataAccessObject {

    private DayData[] arr = new DayData[7];

    private static final DataAccessObject instance = new DataAccessObject();

    //Singleton class.... Synchronize for thread safety... when multi threading
    private DataAccessObject(){
        // This is filling dummy data... change when DB is setup, because data will not yet be available when this singleton is being created,
        fillWeekData();             //so can't fill the week's data
    }

    public static DataAccessObject getInstance(){
        return instance;
    }



    public DayData[] getArray(){
        return arr;
    }


    //Temp method to fill dummy data
    private void fillWeekData(){
        for(int i=0;i<7;i++){
            arr[i]=new DayData();
            Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.add(Calendar.DATE,-i);
            Date d=calendar.getTime();
            calendar.setTime(d);
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);

            Long startOfDayTimeStamp=calendar.getTimeInMillis()/1000;

            Long data=ThreadLocalRandom.current().nextLong(startOfDayTimeStamp-75*6*60, startOfDayTimeStamp-75*6*60+1);
            Date date=new Date((data)*1000);

            Random r=new Random();
            arr[i].setSleep_score(r.nextInt(100-40)+40);
            Long sleep_time=ThreadLocalRandom.current().nextLong(startOfDayTimeStamp-75*6*60,startOfDayTimeStamp-55*6*60);
            Long wake_up_time=ThreadLocalRandom.current().nextLong(startOfDayTimeStamp+5*6*60,startOfDayTimeStamp+20*6*60);
            arr[i].setSleep_time(sleep_time);
            arr[i].setWoke_up_time(wake_up_time);
            arr[i].setTotal_sleep(wake_up_time-sleep_time);
            arr[i].setTime_took(ThreadLocalRandom.current().nextLong(5,80));
            arr[i].setNumber_of_woke_up(r.nextInt(15));
            arr[i].setNumber_of_apnea_epoch(r.nextInt(15));
            arr[i].setSnore_time(ThreadLocalRandom.current().nextLong(5,50));
        }
    }
}
