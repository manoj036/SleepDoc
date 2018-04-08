package com.example.manojkumar.practiceui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manojkumar.practiceui.R;
import com.example.manojkumar.practiceui.firebase.EnvironmentalData;
import com.example.manojkumar.practiceui.model.KeyEnvironmentData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Manoj Kumar on 05-03-2018.
 */

public class EnvironmentFragment extends Fragment {

    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "Date Fragment";
    private DatabaseReference mDatabaseReference;
    public static ArrayList<ArrayList<KeyEnvironmentData>> keyEnvArray = new ArrayList<ArrayList<KeyEnvironmentData>>();

    private static ArrayList<KeyEnvironmentData> mKeyEnvironmentData;
    private int mPage;
    View mView;

    public static EnvironmentFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EnvironmentFragment fragment = new EnvironmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.environment_fragment, container, false);
//          int diff = -mPage;
            getEnvironmentData();
//            mView[mPage] = view;
        }
        return mView;
    }

    private void updateFragment(View view) {
        int index;
        index = 6 - mPage;
        Log.d(TAG, "Page Number in update fragment: " + mPage + " Key Value: " + keyEnvArray.get(index).get(0).getTime());
//        Log.d(TAG, "Size in update fragment: " + keyEnvArray.get(2).size());
        Log.d(TAG, "keyEnvData Fragment: " + keyEnvArray.get(index).get(0).getEnvironmentalData().toString());
        Log.d(TAG, "keyEnvData Fragment: " + keyEnvArray.get(index).get(1).getEnvironmentalData().toString());
    }

    private void getEnvironmentData() {
        int currentPage = 6 - mPage;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DATE, -currentPage);
        Date d = calendar.getTime();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        Long startOfDayTimeStamp = calendar.getTimeInMillis() / 1000;

        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child("users_health_data")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(startOfDayTimeStamp.toString());

//            Fetching EnvironmentData from the server
        keyEnvArray.add(new ArrayList<>());
        mDatabaseReference
                .child("environmental_data")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            KeyEnvironmentData keyEnvironmentData = new KeyEnvironmentData();
                            keyEnvironmentData.setTime(Integer.parseInt(ds.getKey()));
                            keyEnvironmentData.setEnvironmentalData(ds.getValue(EnvironmentalData.class));
                            keyEnvArray.get(currentPage).add(keyEnvironmentData);
//                            Log.d(TAG, "Page Number is: " + mPage);
//                            updateFragment(getView());
//                            Log.d(TAG, "Generic keyEnvData: " + keyEnvironmentData.getEnvironmentalData().toString());
                        }
                        for (int i = 0; i < keyEnvArray.get(0).size(); i++) {
                            Log.d(TAG, "Getting Data for currentPage: " + currentPage);
                            Log.d(TAG, "on Current page Calender Day is: " + calendar.getTime());
                            Log.d(TAG, "Timestamp: " + keyEnvArray.get(currentPage).get(i).getTime() + "        keyEnvData Fragment: " + keyEnvArray.get(0).get(i).getEnvironmentalData().toString());
                        }
//                        updateFragment(getView());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}