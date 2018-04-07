package com.example.manojkumar.practiceui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.manojkumar.practiceui.adapter.WeekSummaryPagerAdapter;

public class HeartRateActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager viewPager = findViewById(R.id.pager);
        WeekSummaryPagerAdapter pagerAdapter = new WeekSummaryPagerAdapter("HeartRate",getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(7);
        viewPager.setCurrentItem(7);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
