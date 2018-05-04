package com.example.manojkumar.practiceui.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.manojkumar.practiceui.R;
import com.example.manojkumar.practiceui.firebase.SleepData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;


public class SleepSummaryFragment extends Fragment {

	private static final String ARG_PAGE = "ARG_PAGE";
	private static final String TAG = "Date Fragment";
	private static SleepData[] sleepDataArray;
	private static SleepData sleepData;
	View[] mView = new View[7];
	private ProgressBar progressBar;
	private TextView sleepScore, number_of_woke_up, number_of_apnea_epoch, snore_time, time_took;
	private TextView fell_asleep_hr, fell_asleep_min, woke_up_hr;
	private TextView woke_up_min, total_slept_hr, total_slept_min, tv_greeting;
	private int mPage;
	private boolean isVisible;
	private boolean isStarted;

	public static SleepSummaryFragment newInstance(int page, SleepData[] val) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		SleepSummaryFragment fragment = new SleepSummaryFragment();
		fragment.setArguments(args);
		sleepDataArray = val;
		Log.d(TAG, "newInstance: Page Id  is: " + page);
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
//        if (mView[mPage] == null) {
		View view = inflater.inflate(R.layout.sleep_summary_fragment, container, false);
		sleepData = sleepDataArray[mPage];
		EditFragment(view);
//            mView[mPage] = view;
//        }
//        return mView[mPage];
		return view;
	}

	@SuppressLint("SetTextI18n")
	private void EditFragment(View view) {
		progressBar = view.findViewById(R.id.sleepScorePB);
		sleepScore = view.findViewById(R.id.sleep_score);
		number_of_woke_up = view.findViewById(R.id.number_of_woke_up);
		number_of_apnea_epoch = view.findViewById(R.id.number_of_apnea_epoch);
		snore_time = view.findViewById(R.id.snore_time);
		time_took = view.findViewById(R.id.time_took);
		fell_asleep_hr = view.findViewById(R.id.fell_asleep_hr);
		fell_asleep_min = view.findViewById(R.id.fell_asleep_min);
		woke_up_hr = view.findViewById(R.id.woke_up_hr);
		woke_up_min = view.findViewById(R.id.woke_up_min);
		total_slept_hr = view.findViewById(R.id.total_slept_hr);
		total_slept_min = view.findViewById(R.id.total_slept_min);
		tv_greeting = view.findViewById(R.id.tv_greeting);

		if (sleepDataArray[mPage] != null) {
			Log.d(TAG, "EditFragment: Here");
			sleepScore.setText(String.valueOf(sleepData.getSleep_score()));

			if (sleepData.getSleep_score() >= 80) {
				progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress_drawable));
				sleepScore.setTextColor(Color.parseColor("#00ff00"));
				tv_greeting.setText("Your score is better than 90% of SleepDoc+ users");
			} else if (sleepData.getSleep_score() >= 60) {
				progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.yellow_progress_drawable));
				sleepScore.setTextColor(Color.parseColor("#ffff00"));
				tv_greeting.setText("Your score is better than 60% of SleepDoc+ users");
			} else {
				progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.red_progress_drawable));
				sleepScore.setTextColor(Color.parseColor("#ff0000"));
				tv_greeting.setText("Your score is better than 30% of SleepDoc+ users");
			}

			NumberFormat f = new DecimalFormat("00");

			number_of_woke_up.setText(String.valueOf(sleepData.getNumber_of_woke_up()));
			number_of_apnea_epoch.setText(String.valueOf(sleepData.getNumber_of_apnea_epoch()));
			snore_time.setText(String.valueOf(sleepData.getSnore_time()));
			time_took.setText(String.valueOf(sleepData.getTime_took()));
			Calendar calendar = Calendar.getInstance();

			calendar.setTimeInMillis(sleepData.getSleep_time() * 1000);
			fell_asleep_hr.setText(String.valueOf(calendar.get(Calendar.HOUR)));
			fell_asleep_min.setText(String.valueOf(f.format(calendar.get(Calendar.MINUTE))));

			calendar.setTimeInMillis(sleepData.getWoke_up_time() * 1000);
			woke_up_hr.setText(String.valueOf(calendar.get(Calendar.HOUR)));
			woke_up_min.setText(String.valueOf(f.format(calendar.get(Calendar.MINUTE))));

			total_slept_hr.setText(String.valueOf((int) (sleepData.getTotal_sleep() / 60 / 60)));
			total_slept_min.setText(String.valueOf(f.format((sleepData.getTotal_sleep() / 60) % 60)));

		}
	}

	@Override
	public void onStart() {
		super.onStart();
		isStarted = true;
		if (sleepDataArray[mPage] != null && isVisible)
			animateTextView(0, sleepDataArray[mPage].getSleep_score(), sleepScore);
	}

	@Override
	public void onStop() {
		super.onStop();
		isStarted = false;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isVisible = isVisibleToUser;
		if (sleepDataArray[mPage] != null && isVisible && isStarted)
			animateTextView(0, sleepDataArray[mPage].getSleep_score(), sleepScore);
	}

	public void animateTextView(int initialValue, int finalValue, final TextView textview) {
		ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, sleepDataArray[mPage].getSleep_score() * 100);
		objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		objectAnimator.setDuration(1000);
		objectAnimator.start();

		ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
		valueAnimator.setDuration(1000);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				textview.setText(valueAnimator.getAnimatedValue().toString());
			}
		});
		valueAnimator.start();

	}
}