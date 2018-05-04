package com.example.manojkumar.practiceui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.manojkumar.practiceui.adapter.WeekSummaryPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.Calendar;
import java.util.Date;

public class HeartRateActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager viewPager = findViewById(R.id.pager);
        WeekSummaryPagerAdapter pagerAdapter = new WeekSummaryPagerAdapter("HeartRate",getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
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
    }

    @Override
    protected void onStart() {
        CURRENT_BTM_ACTIVITY =R.id.heart_rate_button;
        super.onStart();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_heart_rate;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.heart_rate_button;
    }

}
