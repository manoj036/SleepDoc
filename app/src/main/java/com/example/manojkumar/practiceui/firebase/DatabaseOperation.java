package com.example.manojkumar.practiceui.firebase;

import android.util.Log;

import com.example.manojkumar.practiceui.model.KeyVitalsData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.os.SystemClock.sleep;

/**
 * Created by LaxmiKantPC on 30-Dec-17.
 *
 * @Name : DatabaseOperation
 * @Description : This class is the main class responsible for comitting data to firebase and retrieving data from the same
 */

public class DatabaseOperation {

    //Class level variables
    private static final String TAG = "DatabaseOperation";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public static List<SleepData> mSleepDataList = new ArrayList<>();

    private FirebaseAuth mFirebaseAuth;
    private String userId = mFirebaseAuth.getInstance().getCurrentUser().getUid();

    String currentDate, currentTime;

    //Refernces of the PoJo classes for storing the JSON values
    Person person;
    EnvironmentalData environmentalData;
    VitalsData vitalsData;
    SleepData sleepData;
    String dateFinal;
    //Map to store vitals data
    public static HashMap<String, VitalsData> vitalsDataMap = new HashMap<>();

    public DatabaseOperation() {
    }

    /*@Descrption : This methods sets data to the database
          @Parameters : void - the values are hardcoded for now later they will be sent as a list of parametrs from the sensors
        */
    public void setDataBaseData() {
        person = new Person();
        environmentalData = new EnvironmentalData();
        vitalsData = new VitalsData();
        sleepData = new SleepData();

        /**
         * Set personal profile information
         */
        person.setName("Laxmi Kant");
        person.setAge(45);

        final Map<String, Object> data = new HashMap<>();
        data.put("Name", person.getName());
        data.put("Age", person.getAge());

        for (int i = 0; i < 7; i++) {
            /**
             * set environmental data
             */
            environmentalData.setTemp(new Random().nextInt(20) + 10);
            environmentalData.setHumidity(new Random().nextInt(50) + 20);
            environmentalData.setAq(new Random().nextInt(30) + 20);

            final Map<String, Object> environmentalDataArray = new HashMap<>();
            environmentalDataArray.put("temp", environmentalData.getTemp());
            environmentalDataArray.put("humidity", environmentalData.getHumidity());
            environmentalDataArray.put("aq", environmentalData.getAq());


            /**
             * set vitals information
             */
            vitalsData.setResp_rate(new Random().nextInt(12) + 8);
            vitalsData.setResp_var(new Random().nextInt(2) + 2);
            vitalsData.setHeart_rate(new Random().nextInt(60) + 60);
            vitalsData.setHeart_var(new Random().nextInt(10) + 10);
            vitalsData.setBody_movement(new Random().nextInt(4));
            vitalsData.setLight(new Random().nextInt(100) + 100);
            vitalsData.setNoise(new Random().nextInt(20) + 30);
            vitalsData.setSleep_stage(new Random().nextInt(4));
            vitalsData.setIs_apnea(false);
            vitalsData.setIs_snoring(true);

            final Map<String, Object> vitalsDataArray = new HashMap<>();
            vitalsDataArray.put("resp_rate", vitalsData.getResp_rate());
            vitalsDataArray.put("resp_var", vitalsData.getResp_var());
            vitalsDataArray.put("heart_rate", vitalsData.getHeart_rate());
            vitalsDataArray.put("heart_var", vitalsData.getHeart_var());
            vitalsDataArray.put("body_movement", vitalsData.getBody_movement());
            vitalsDataArray.put("light", vitalsData.getLight());
            vitalsDataArray.put("noise", vitalsData.getNoise());
            vitalsDataArray.put("sleep_stage", vitalsData.getSleep_stage());
            vitalsDataArray.put("is_apnea", vitalsData.isIs_apnea());
            vitalsDataArray.put("is_snoring", vitalsData.isIs_snoring());


            /**
             * set sleep data
             */
            Date sleep_time = new Date();

            sleepData.setSleepTime(sleep_time.getTime() / 1000);
            sleepData.setWokeUpTime(sleep_time.getTime() / 1000 + new Random().nextInt(5000) + 20000);
            sleepData.setTimeTook(new Random().nextInt(10) + 10);
            sleepData.setOutOfBedTime(new Random().nextInt(10) + 10);
            sleepData.setSnoreTime(new Random().nextInt(10) + 20);
            sleepData.setNumberOfWokeUp(new Random().nextInt(4));
            sleepData.setNumberOfApneaEpoch(new Random().nextInt(10));
            sleepData.setSleepScore(new Random().nextInt(50) + 50);
            sleepData.setSleep_score_better_than(new Random().nextInt(50) + 50);
            sleepData.setMax_resp(new Random().nextInt(6) + 18);
            sleepData.setMin_resp(new Random().nextInt(4) + 6);
            sleepData.setAvg_resp(new Random().nextInt(12) + 4);
            sleepData.setVar_resp(new Random().nextInt(3) + 3);
            sleepData.setMax_heart(new Random().nextInt(20) + 110);
            sleepData.setMin_heart(new Random().nextInt(50) + 10);
            sleepData.setAvg_heart(new Random().nextInt(70) + 20);
            sleepData.setVar_heart(new Random().nextInt(10) + 10);
            sleepData.setMax_temp(new Random().nextInt(10) + 20);
            sleepData.setMin_temp(new Random().nextInt(10) + 5);
            sleepData.setAvg_temp(new Random().nextInt(15) + 5);
            sleepData.setMax_humidity(new Random().nextInt(50) + 20);
            sleepData.setMin_humidity(new Random().nextInt(20) + 20);
            sleepData.setAvg_humidity(new Random().nextInt(30) + 20);
            sleepData.setMax_aq(new Random().nextInt(30) + 30);
            sleepData.setMin_aq(new Random().nextInt(20) + 10);
            sleepData.setAvg_aq(new Random().nextInt(25) + 10);
            sleepData.setMax_light(new Random().nextInt(3000) + 1000);
            sleepData.setMin_light(new Random().nextInt(1000) + 100);
            sleepData.setAvg_light(new Random().nextInt(1500) + 1500);
            sleepData.setMax_noise(new Random().nextInt(20) + 30);
            sleepData.setMin_noise(new Random().nextInt(10) + 10);
            sleepData.setAvg_noise(new Random().nextInt(15) + 15);

            final Map<String, Object> sleepDataArray = new HashMap<>();
            sleepDataArray.put("sleep_time", sleepData.getSleepTime());
            sleepDataArray.put("woke_up_time", sleepData.getWokeUpTime());
            sleepDataArray.put("total_sleep", sleepData.getTotalSleep());
            sleepDataArray.put("time_took", sleepData.getTimeTook());
            sleepDataArray.put("out_of_bed_time", sleepData.getOutOfBedTime());
            sleepDataArray.put("snore_time", sleepData.getSnoreTime());
            sleepDataArray.put("number_of_woke_up", sleepData.getNumberOfWokeUp());
            sleepDataArray.put("number_of_apnea_epoch", sleepData.getNumberOfApneaEpoch());
            sleepDataArray.put("sleep_score", sleepData.getSleepScore());
            sleepDataArray.put("sleep_score_better_than", sleepData.getSleep_score_better_than());
            sleepDataArray.put("max_resp", sleepData.getMax_resp());
            sleepDataArray.put("min_resp", sleepData.getMin_resp());
            sleepDataArray.put("avg_resp", sleepData.getAvg_resp());
            sleepDataArray.put("var_resp", sleepData.getVar_resp());
            sleepDataArray.put("max_heart", sleepData.getMax_heart());
            sleepDataArray.put("min_heart", sleepData.getMin_heart());
            sleepDataArray.put("avg_heart", sleepData.getAvg_heart());
            sleepDataArray.put("var_heart", sleepData.getVar_heart());
            sleepDataArray.put("max_temp", sleepData.getMax_temp());
            sleepDataArray.put("min_temp", sleepData.getMin_temp());
            sleepDataArray.put("avg_temp", sleepData.getAvg_temp());
            sleepDataArray.put("max_humidity", sleepData.getMax_humidity());
            sleepDataArray.put("min_humidity", sleepData.getMin_humidity());
            sleepDataArray.put("avg_humidity", sleepData.getAvg_humidity());
            sleepDataArray.put("max_aq", sleepData.getMax_aq());
            sleepDataArray.put("min_aq", sleepData.getMin_aq());
            sleepDataArray.put("avg_aq", sleepData.getAvg_aq());
            sleepDataArray.put("max_light", sleepData.getMax_light());
            sleepDataArray.put("min_light", sleepData.getMin_light());
            sleepDataArray.put("avg_light", sleepData.getAvg_light());
            sleepDataArray.put("max_noise", sleepData.getMax_noise());
            sleepDataArray.put("min_noise", sleepData.getMin_noise());
            sleepDataArray.put("avg_noise", sleepData.getAvg_noise());

            //push the data
//        final String keyOld = mDatabase.child("users").child("users_health_data").child((userId.toString()));

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String date = simpleTimeFormat.format(calendar.getTime());

            int tempTime = (int) (System.currentTimeMillis() / 1000);
            currentTime = Integer.toString(tempTime);
            tempTime = (tempTime - (tempTime % (24 * 60 * 60)) - (i * 24 * 60 * 60));
            String time = simpleTimeFormat.format(calendar.getTime());

            currentDate = Integer.toString(tempTime);
            Log.d(TAG, "setDataBaseData: CurrentDate: " + currentDate);

            Date today = new Date();
            long now = today.getTime();

            mDatabase.child("users/users_health_data/" + userId + "/" + currentDate + "/" + "vitals_data/" + currentTime + "/").updateChildren(vitalsDataArray);
            mDatabase.child("users/users_health_data/" + userId + "/" + currentDate + "/" + "environmental_data/" + currentTime + "/").updateChildren(environmentalDataArray);
            mDatabase.child("users/users_health_data/" + userId + "/" + currentDate + "/" + "sleep_data/data").updateChildren(sleepDataArray);
            sleep(1000);
        }
    }

    /*@Descrption : This methods sets data to the database
          @Parameters : void - the values are hardcoded for now later they will be sent as a list of parametrs from the sensors
        */
    public void setEnvironmentalData() {
        environmentalData = new EnvironmentalData();

        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String date = simpleTimeFormat.format(calendar.getTime());

            int tempTime = (int) (System.currentTimeMillis() / 1000);
            int temp = tempTime;
            tempTime = (tempTime - (tempTime % (24 * 60 * 60)) - (i * 24 * 60 * 60));
            String time = simpleTimeFormat.format(calendar.getTime());

            currentDate = Integer.toString(tempTime);
            Log.d(TAG, "setDataBaseData: CurrentDate: " + currentDate);

            for (int j = 0; j < 50; j++) {

                currentTime = Integer.toString(tempTime+600*j);

                /**
                 * set environmental data
                 */
                environmentalData.setTemp(new Random().nextInt(20) + 10);
                environmentalData.setHumidity(new Random().nextInt(50) + 20);
                environmentalData.setAq(new Random().nextInt(30) + 20);

                mDatabase.child("users/users_health_data/" + userId + "/" + currentDate + "/" + "environmental_data/" + currentTime + "/").setValue(environmentalData);

            }
        }
    }

    /*@Descrption : This methods gets data from the database and parses the JSON data to set in the respective class
      @Parameters : void
    */
    public List<SleepData> getSleepData() {
//        mSleepData = new SleepData[7];
        Log.d(TAG, "getSleepData: getSleepData Called");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = simpleTimeFormat.format(calendar.getTime());

        int tempTime = (int) (System.currentTimeMillis() / 1000);
        currentTime = Integer.toString(tempTime);
        tempTime = (tempTime - (tempTime % (24 * 60 * 60)));
        String time = simpleTimeFormat.format(calendar.getTime());

        currentDate = Integer.toString(tempTime);
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "\n\n\ngetValue: " + dataSnapshot.getValue());

                VitalsData tempVitalData;
                List<KeyVitalsData> keyVitalsDataList = new ArrayList<>();
                KeyVitalsData keyVitalsData = new KeyVitalsData();

                //Traverse till key and fetch all the children and put it in a map for processing
                for (DataSnapshot ds : dataSnapshot.child("users")
                        .child("users_health_data")
                        .child(userId)
                        .child(currentDate)
                        .child("vitals_data").getChildren()) {
                    //Caste to class
                    tempVitalData = ds.getValue(VitalsData.class);
                    int dataTime = Integer.parseInt(ds.getKey());

                    keyVitalsData.setTime(dataTime);
                    keyVitalsData.setVitalsData(tempVitalData);

                    keyVitalsDataList.add(keyVitalsData);

//                    Log.d(TAG, "vitalDataSize: " + tempVitalData);
//                    Log.d(TAG, "onDataChange: SleepData: " + ds.getValue(SleepData.class));
                    //EditText val = (EditText)findViewById(R.id.debug);
                    //val.setText(""+tempVitalData.toString());
//                    vitalsDataMap.put(ds.getKey(), tempVitalData);
                }
//                Log.d(TAG, "vitalMap: " + vitalsDataMap);

                for (int i = 0; i < keyVitalsDataList.size(); i++) {
                    vitalsData = keyVitalsDataList.get(i).getVitalsData();
                    int dataTime = keyVitalsDataList.get(i).getTime();
//                    Log.d(TAG, "onDataChange: "  + dataTime + vitalsData);
                }

                int index = 0;
                //Traverse till key and fetch all the children and put it in a map for processing
                for (DataSnapshot ds : dataSnapshot.child("users")
                        .child("users_health_data")
                        .child(userId)
                        .child(currentDate)
                        .child("sleep_data").getChildren()) {
                    //Caste to class
                    SleepData tempSleepData = ds.getValue(SleepData.class);
//                    Log.d(TAG, "SleepData: " + tempSleepData);
//                    Log.d(TAG, "onDataChange: getTheKey: " + tempSleepData);
                    mSleepDataList.add(index, tempSleepData);
                    Log.d(TAG, "Database Operation mSleepData: " + mSleepDataList.get(index));
                    index = index + 1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mSleepDataList;
    }
}
