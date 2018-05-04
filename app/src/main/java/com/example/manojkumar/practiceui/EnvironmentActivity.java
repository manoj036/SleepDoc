package com.example.manojkumar.practiceui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.manojkumar.practiceui.adapter.WeekSummaryPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.Calendar;
import java.util.Date;

public class EnvironmentActivity extends BaseActivity {

    DatabaseReference myRef;

    @Override
    protected void onStart() {
        CURRENT_BTM_ACTIVITY=R.id.environment_button;
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = findViewById(R.id.pager);
        WeekSummaryPagerAdapter pagerAdapter = new WeekSummaryPagerAdapter("Environment",getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(7);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int diff = position-6;
                TextView dateText = findViewById(R.id.todays_date);
                Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.DATE,diff);
                Date d=calendar.getTime();

                String day=(String) android.text.format.DateFormat.format("EEEE",d);
                String intMonth=(String) android.text.format.DateFormat.format("MMM",d);
                String date=(String) android.text.format.DateFormat.format("dd",d);
                String finalDay=day+", "+intMonth+" "+date;
                dateText.setText(finalDay);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SmartTabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setViewPager(viewPager);

        myRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Environment Data")
                .child(simpleDateFormat.format(Calendar.getInstance().getTime()));

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" +dataSnapshot.child("Gravity").getValue());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_environment;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.environment_button;
    }
}
