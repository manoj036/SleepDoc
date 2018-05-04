package com.example.manojkumar.practiceui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.example.manojkumar.practiceui.adapter.WeekSummaryPagerAdapter;
import com.example.manojkumar.practiceui.firebase.VitalsData;
import com.example.manojkumar.practiceui.model.KeyVitalsData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class RespirationActivity extends BaseActivity {
    private static final String TAG = "RespirationActivity";
    ViewPager viewPager;

    private DatabaseReference mDatabaseReference;
    public static ArrayList<ArrayList<KeyVitalsData>> keyVitalsDataArray = new ArrayList<ArrayList<KeyVitalsData>>();

    @Override
    protected void onStart() {
        CURRENT_BTM_ACTIVITY = R.id.respiration_button;
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Respiration Summary");
        getSupportActionBar().show();

        getRespirationData();
        viewPager = findViewById(R.id.pager);
        WeekSummaryPagerAdapter pagerAdapter = new WeekSummaryPagerAdapter("Respiration", getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(7);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int diff = position - 6;
                TextView dateText = findViewById(R.id.todays_date);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, diff);
                Date d = calendar.getTime();

                String day = (String) android.text.format.DateFormat.format("EEEE", d);
                String intMonth = (String) android.text.format.DateFormat.format("MMM", d);
                String date = (String) android.text.format.DateFormat.format("dd", d);
                String finalDay = day + ", " + intMonth + " " + date;
                dateText.setText(finalDay);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SmartTabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setViewPager(viewPager);
        }

    @Override
    public int getContentViewId() {
        return R.layout.respiration_activity;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.respiration_button;
    }

    private void getRespirationData() {
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.add(Calendar.DATE, -i);
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

            keyVitalsDataArray.add(new ArrayList<>());
            int finalI = i;
            mDatabaseReference
                    .child("vitals_data")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                KeyVitalsData keyVitalsData = new KeyVitalsData();
                                keyVitalsData.setTime(Integer.parseInt(ds.getKey()));
                                keyVitalsData.setVitalsData(ds.getValue(VitalsData.class));
                                keyVitalsDataArray.get(finalI).add(keyVitalsData);
                                Log.d(TAG, "Data in Respiration Activity: " + keyVitalsData.getVitalsData().toString());
                            }
							final Handler handler = new Handler();
							handler.removeCallbacks(runnable);
							handler.postDelayed(runnable, 1000);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    Runnable runnable = new Runnable() {
		@Override
		public void run() {
			viewPager.getAdapter().notifyDataSetChanged();

		}
	};
}
