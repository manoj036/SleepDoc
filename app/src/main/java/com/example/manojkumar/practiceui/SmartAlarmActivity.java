package com.example.manojkumar.practiceui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SmartAlarmActivity extends BaseActivity {

	@Override
	protected void onResume() {
		navigationView.getMenu().getItem(3).setChecked(true);
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView title = findViewById(R.id.toolbar_title);
		title.setText(R.string.smart_alarm_title);
	}

	@Override
	public int getContentViewId() {
		return R.layout.activity_smart_alarm;
	}

	@Override
	public int getNavigationMenuItemId() {
		return 0;
	}
}
