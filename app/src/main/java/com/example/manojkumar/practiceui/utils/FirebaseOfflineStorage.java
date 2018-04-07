package com.example.manojkumar.practiceui.utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mbreath on 07/04/18.
 */

public class FirebaseOfflineStorage extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
