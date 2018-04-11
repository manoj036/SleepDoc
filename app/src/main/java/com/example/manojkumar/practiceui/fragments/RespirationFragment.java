package com.example.manojkumar.practiceui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.manojkumar.practiceui.R;
import com.example.manojkumar.practiceui.firebase.VitalsData;
import com.example.manojkumar.practiceui.model.KeyVitalsData;
import com.example.manojkumar.practiceui.utils.MinutesAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Manoj Kumar on 05-03-2018.
 */

public class RespirationFragment extends Fragment {

    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "Date Fragment";
    private DatabaseReference mDatabaseReference;
    public static ArrayList<ArrayList<KeyVitalsData>> keyVitalsDataArray = new ArrayList<ArrayList<KeyVitalsData>>();
    private int mPage;
    View mView;

    private LineChart mRespirationChart;

    public static RespirationFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RespirationFragment fragment = new RespirationFragment();
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
            mView = inflater.inflate(R.layout.respiration_fragment, container, false);
            mRespirationChart = (mView).findViewById(R.id.respiration_chart);
            mRespirationChart.setScaleEnabled(false);
            mRespirationChart.setDoubleTapToZoomEnabled(false);
            mRespirationChart.animateX(1000);
            getRespirationData();
        }
        return mView;
    }

    private void EditFragment(View view) {
    }

    private void getRespirationData() {
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

        keyVitalsDataArray.add(new ArrayList<>());
        mDatabaseReference
                .child("vitals_data")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            KeyVitalsData keyVitalsData = new KeyVitalsData();
                            keyVitalsData.setTime(Integer.parseInt(ds.getKey()));
                            keyVitalsData.setVitalsData(ds.getValue(VitalsData.class));
                            keyVitalsDataArray.get(currentPage).add(keyVitalsData);
                            Log.d(TAG, "keyLightNoise: " + keyVitalsData.getVitalsData().toString());
                        }
                        updateRespiration(getView());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void updateRespiration(View view) {
        int index;
        index = 6 - mPage;
        /***
         * Setting Light Plot
         */
        ArrayList<Entry> yVals1 = new ArrayList<>();
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (int i = 0; i < keyVitalsDataArray.get(index).size(); i++) {
            float val = (float) keyVitalsDataArray.get(index).get(i).getVitalsData().getResp_rate();
            yVals1.add(new Entry(i, val));
            descriptiveStatistics.addValue(val);
        }
        int max = (int) descriptiveStatistics.getMax();
        int min = (int) descriptiveStatistics.getMin();
        int avg = (int) descriptiveStatistics.getMean();
        int std = (int) descriptiveStatistics.getStandardDeviation();

        TextView resp_max = (TextView) view.findViewById(R.id.max_respiration);
        resp_max.setText(String.valueOf(max));
        ProgressBar resp_max_pb = (ProgressBar) view.findViewById(R.id.pb_max_respiration);
        resp_max_pb.setProgress(max);
        TextView resp_min = (TextView) view.findViewById(R.id.min_respiration);
        resp_min.setText(String.valueOf(min));
        ProgressBar resp_min_pb = (ProgressBar) view.findViewById(R.id.pb_min_respiration);
        resp_min_pb.setProgress(min);
        TextView resp_avg = (TextView) view.findViewById(R.id.avg_respiration);
        resp_avg.setText(String.valueOf(avg));
        ProgressBar resp_avg_pb = (ProgressBar) view.findViewById(R.id.pb_avg_respiration);
        resp_max_pb.setProgress(avg);
        TextView resp_var = (TextView) view.findViewById(R.id.var_respiration);
        resp_var.setText(String.valueOf(std));
        ProgressBar resp_var_pb = (ProgressBar) view.findViewById(R.id.pb_var_respiration);
        resp_var_pb.setProgress(std);

        LineDataSet set4;
        set4 = new LineDataSet(yVals1, "Respiration Rate (breath per minute)");
        set4.setColor(Color.GREEN);
        set4.setDrawCircles(false);
        set4.setLineWidth(3f);
        set4.setDrawValues(false);
        set4.setHighlightEnabled(false);
        set4.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set4.setCubicIntensity(0.1f);
        LineData data4 = new LineData(set4);
        MinutesAxisValueFormatter axisFormatter4 = new MinutesAxisValueFormatter(keyVitalsDataArray.get(index).get(0).getTime());
        mRespirationChart.setData(data4);
        mRespirationChart.getDescription().setEnabled(false);
        mRespirationChart.getXAxis().setValueFormatter(axisFormatter4);
        mRespirationChart.getAxisLeft().setTextColor(Color.LTGRAY);
        mRespirationChart.getAxisLeft().setTextSize(12);
        mRespirationChart.getAxisLeft().setDrawAxisLine(false);
        mRespirationChart.getAxisLeft().setLabelCount(6);
        Legend legend4 = mRespirationChart.getLegend();
        legend4.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend4.setForm(Legend.LegendForm.CIRCLE);
        legend4.setTextColor(Color.LTGRAY);
        legend4.setTextSize(12);
        YAxis right4 = mRespirationChart.getAxisRight();
        right4.setDrawLabels(false); // no axis labels
        right4.setDrawGridLines(false);
        right4.setAxisMaximum(28);
        right4.setDrawAxisLine(false); // no axis line
        YAxis left4 = mRespirationChart.getAxisLeft();
        left4.setValueFormatter(new LargeValueFormatter());
        XAxis xAxis4 = mRespirationChart.getXAxis();
        xAxis4.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis4.setDrawAxisLine(true);
        xAxis4.setDrawGridLines(false);
        xAxis4.setLabelCount(5, false);
        xAxis4.setTextColor(Color.LTGRAY);
        xAxis4.setTextSize(10);
    }
}