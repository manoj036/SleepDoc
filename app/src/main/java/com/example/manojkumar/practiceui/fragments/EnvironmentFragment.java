package com.example.manojkumar.practiceui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manojkumar.practiceui.R;
import com.example.manojkumar.practiceui.adapter.TimelineAdapter;
import com.example.manojkumar.practiceui.firebase.EnvironmentalData;
import com.example.manojkumar.practiceui.firebase.VitalsData;
import com.example.manojkumar.practiceui.model.KeyEnvironmentData;
import com.example.manojkumar.practiceui.model.KeyVitalsData;
import com.example.manojkumar.practiceui.model.Timeline;
import com.example.manojkumar.practiceui.utils.Minutes20AxisValueFormatter;
import com.example.manojkumar.practiceui.utils.MinutesAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    public static ArrayList<ArrayList<KeyVitalsData>> keyLightNoiseArray = new ArrayList<ArrayList<KeyVitalsData>>();
    private static ArrayList<KeyVitalsData> mKeyLightNoise;
    private int mPage;
    View mView;

    private SlidingUpPanelLayout mLayout;
    private LineChart temperatureChart, humidityChart, aqChart, lightChart, noiseChart;
    private RecyclerView recyclerView;
    private TimelineAdapter mAdapter;
    private List<Timeline> mTimelineList = new ArrayList<>();

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

            recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
            mAdapter = new TimelineAdapter(mTimelineList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            prepareData();

            temperatureChart = (LineChart) mView.findViewById(R.id.env_chart1);
            temperatureChart.setScaleEnabled(false);
            temperatureChart.setDoubleTapToZoomEnabled(false);
            temperatureChart.animateX(1000);

            humidityChart = (LineChart) mView.findViewById(R.id.env_chart2);
            humidityChart.setDoubleTapToZoomEnabled(false);
            humidityChart.animateX(1000);
            humidityChart.setScaleEnabled(false);

            aqChart = (LineChart) mView.findViewById(R.id.env_chart3);
            aqChart.setDoubleTapToZoomEnabled(false);
            aqChart.animateX(1000);
            aqChart.setScaleEnabled(false);

            lightChart = (LineChart) mView.findViewById(R.id.env_chart4);
            lightChart.setDoubleTapToZoomEnabled(false);
            lightChart.animateX(1000);
            lightChart.setScaleEnabled(false);

            noiseChart = (LineChart) mView.findViewById(R.id.env_chart5);
            noiseChart.setDoubleTapToZoomEnabled(false);
            noiseChart.animateX(1000);
            noiseChart.setScaleEnabled(false);

            getEnvironmentData();
            getLightNoiseData();

            mLayout = (SlidingUpPanelLayout) mView.findViewById(R.id.sliding_layout);
            mLayout.setAnchorPoint(1.0f);
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                }

                @Override
                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                    Log.i(TAG, "onPanelStateChanged " + newState);
                }
            });
            mLayout.setFadeOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            });
        }
        return mView;
    }

    private void updateFragment(View view) {
        int index;
        index = 6 - mPage;
        Log.d(TAG, "Page Number in update fragment: " + mPage + " Key Value: " + keyEnvArray.get(index).get(0).getTime());
        Log.d(TAG, "keyEnvData Fragment: " + keyEnvArray.get(index).get(0).getEnvironmentalData().toString());
        Log.d(TAG, "keyEnvData Fragment: " + keyEnvArray.get(index).get(1).getEnvironmentalData().toString());

        /***
         * Setting Tempreture Plot
         */
        ArrayList<Entry> yVals1 = new ArrayList<>();
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (int i = 0; i < keyEnvArray.get(index).size(); i++) {
            float val = (float) keyEnvArray.get(index).get(i).getEnvironmentalData().getTemp();
            yVals1.add(new Entry(i, val));
            descriptiveStatistics.addValue(val);
        }
        int max = (int) descriptiveStatistics.getMax();
        int min = (int) descriptiveStatistics.getMin();
        int avg = (int) descriptiveStatistics.getMean();
        TextView temp_max = (TextView) view.findViewById(R.id.temp_max);
        temp_max.setText(String.valueOf(max));
        TextView temp_min = (TextView) view.findViewById(R.id.temp_min);
        temp_min.setText(String.valueOf(min));
        TextView temp_avg = (TextView) view.findViewById(R.id.temp_avg);
        temp_avg.setText(String.valueOf(avg));

        LineDataSet set1;
        set1 = new LineDataSet(yVals1, "Temperature (ºC)");
        set1.setColor(Color.RED);
        set1.setDrawCircles(false);
        set1.setLineWidth(3f);
        set1.setDrawValues(false);
        set1.setHighlightEnabled(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.1f);

        LineData data = new LineData(set1);
        Minutes20AxisValueFormatter axisFormatter = new Minutes20AxisValueFormatter(keyEnvArray.get(index).get(0).getTime());
        temperatureChart.setData(data);
        temperatureChart.getDescription().setEnabled(false);
        temperatureChart.getXAxis().setValueFormatter(axisFormatter);
        temperatureChart.getAxisLeft().setTextColor(Color.LTGRAY);
        temperatureChart.getAxisLeft().setTextSize(12);
        temperatureChart.getAxisLeft().setDrawAxisLine(false);
        temperatureChart.getAxisLeft().setLabelCount(6);
        temperatureChart.setExtraTopOffset(10);
        Legend legend = temperatureChart.getLegend();
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.LTGRAY);
        legend.setTextSize(12);
        YAxis right = temperatureChart.getAxisRight();
        right.setDrawLabels(false); // no axis labels
        right.setDrawGridLines(false);
        right.setDrawAxisLine(false); // no axis line
        XAxis xAxis = temperatureChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(5, false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(10);
        xAxis.setLabelCount(5, true);

        /**
         * Setting Air Quality Plot
         */
        yVals1 = new ArrayList<>();
        descriptiveStatistics.clear();
        for (int i = 0; i < keyEnvArray.get(index).size(); i++) {
            float val = (float) keyEnvArray.get(index).get(i).getEnvironmentalData().getAq();
            yVals1.add(new Entry(i, val));
            descriptiveStatistics.addValue(val);
        }

        max = (int) descriptiveStatistics.getMax();
        min = (int) descriptiveStatistics.getMin();
        avg = (int) descriptiveStatistics.getMean();
        TextView aq_max = (TextView) view.findViewById(R.id.aq_max);
        aq_max.setText(String.valueOf(max));
        TextView aq_min = (TextView) view.findViewById(R.id.aq_min);
        aq_min.setText(String.valueOf(min));
        TextView aq_avg = (TextView) view.findViewById(R.id.aq_avg);
        aq_avg.setText(String.valueOf(avg));

        LineDataSet set2;
        set2 = new LineDataSet(yVals1, "Air Quality (ppm)");
        set2.setColor(Color.CYAN);
        set2.setDrawCircles(false);
        set2.setLineWidth(3f);
        set2.setDrawValues(false);
        set2.setHighlightEnabled(false);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set2.setCubicIntensity(0.1f);
        LineData data2 = new LineData(set2);
        Minutes20AxisValueFormatter axisFormatter2 = new Minutes20AxisValueFormatter(keyEnvArray.get(index).get(0).getTime());
        humidityChart.setData(data2);
        humidityChart.getDescription().setEnabled(false);
        humidityChart.getXAxis().setValueFormatter(axisFormatter2);
        humidityChart.getAxisLeft().setTextColor(Color.LTGRAY);
        humidityChart.getAxisLeft().setTextSize(12);
        humidityChart.getAxisLeft().setDrawAxisLine(false);
        humidityChart.getAxisLeft().setLabelCount(6);
        humidityChart.setExtraTopOffset(10);
        Legend legend2 = humidityChart.getLegend();
        legend2.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend2.setForm(Legend.LegendForm.CIRCLE);
        legend2.setTextColor(Color.LTGRAY);
        legend2.setTextSize(12);
        YAxis right2 = humidityChart.getAxisRight();
        right2.setDrawLabels(false); // no axis labels
        right2.setDrawGridLines(false);
        right2.setAxisMaximum(28);
        right2.setDrawAxisLine(false); // no axis line
        XAxis xAxis2 = humidityChart.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawAxisLine(true);
        xAxis2.setDrawGridLines(false);
        xAxis2.setLabelCount(5, false);
        xAxis2.setTextColor(Color.LTGRAY);
        xAxis2.setTextSize(10);

        /**
         * Setting Humidity Plot
         */
        yVals1 = new ArrayList<>();
        descriptiveStatistics.clear();
        for (int i = 0; i < keyEnvArray.get(index).size(); i++) {
            float val = (float) keyEnvArray.get(index).get(i).getEnvironmentalData().getHumidity();
            yVals1.add(new Entry(i, val));
            descriptiveStatistics.addValue(val);
        }
        max = (int) descriptiveStatistics.getMax();
        min = (int) descriptiveStatistics.getMin();
        avg = (int) descriptiveStatistics.getMean();
        TextView humi_max = (TextView) view.findViewById(R.id.humi_max);
        humi_max.setText(String.valueOf(max));
        TextView humi_min = (TextView) view.findViewById(R.id.humi_min);
        humi_min.setText(String.valueOf(min));
        TextView humi_avg = (TextView) view.findViewById(R.id.humi_avg);
        humi_avg.setText(String.valueOf(avg));


        LineDataSet set3;
        set3 = new LineDataSet(yVals1, "Humidity (%rH)");
        set3.setColor(Color.YELLOW);
        set3.setDrawCircles(false);
        set3.setLineWidth(3f);
        set3.setDrawValues(false);
        set3.setHighlightEnabled(false);
        set3.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set3.setCubicIntensity(0.1f);
        LineData data3 = new LineData(set3);
        Minutes20AxisValueFormatter axisFormatter3 = new Minutes20AxisValueFormatter(keyEnvArray.get(index).get(0).getTime());
        aqChart.setData(data3);
        aqChart.getDescription().setEnabled(false);
        aqChart.getXAxis().setValueFormatter(axisFormatter3);
        aqChart.getAxisLeft().setTextColor(Color.LTGRAY);
        aqChart.getAxisLeft().setTextSize(12);
        aqChart.getAxisLeft().setDrawAxisLine(false);
        aqChart.getAxisLeft().setLabelCount(6);
        aqChart.setExtraTopOffset(10);
        Legend legend3 = aqChart.getLegend();
        legend3.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend3.setForm(Legend.LegendForm.CIRCLE);
        legend3.setTextColor(Color.LTGRAY);
        legend3.setTextSize(12);
        YAxis right3 = aqChart.getAxisRight();
        right3.setDrawLabels(false); // no axis labels
        right3.setDrawGridLines(false);
        right3.setAxisMaximum(28);
        right3.setDrawAxisLine(false); // no axis line
        XAxis xAxis3 = aqChart.getXAxis();
        xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis3.setDrawAxisLine(true);
        xAxis3.setDrawGridLines(false);
        xAxis3.setLabelCount(5, false);
        xAxis3.setTextColor(Color.LTGRAY);
        xAxis3.setTextSize(10);
    }

    private void updateLightNoise(View view) {
        int index;
        index = 6 - mPage;
        /***
         * Setting Light Plot
         */
        ArrayList<Entry> yVals1 = new ArrayList<>();
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (int i = 0; i < keyLightNoiseArray.get(index).size(); i++) {
            float val = (float) keyLightNoiseArray.get(index).get(i).getVitalsData().getLight();
            yVals1.add(new Entry(i, val));
            descriptiveStatistics.addValue(val);
        }
        int max = (int) descriptiveStatistics.getMax();
        int min = (int) descriptiveStatistics.getMin();
        int avg = (int) descriptiveStatistics.getMean();
        TextView light_max = (TextView) view.findViewById(R.id.light_max);
        light_max.setText(String.valueOf(max));
        TextView light_min = (TextView) view.findViewById(R.id.light_min);
        light_min.setText(String.valueOf(min));
        TextView light_avg = (TextView) view.findViewById(R.id.light_avg);
        light_avg.setText(String.valueOf(avg));


        LineDataSet set4;
        set4 = new LineDataSet(yVals1, "Light (lx)");
        set4.setColor(Color.MAGENTA);
        set4.setDrawCircles(false);
        set4.setLineWidth(3f);
        set4.setDrawValues(false);
        set4.setHighlightEnabled(false);
        set4.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set4.setCubicIntensity(0.1f);
        LineData data4 = new LineData(set4);
        MinutesAxisValueFormatter axisFormatter4 = new MinutesAxisValueFormatter(keyLightNoiseArray.get(index).get(0).getTime());
        lightChart.setData(data4);
        lightChart.getDescription().setEnabled(false);
        lightChart.getXAxis().setValueFormatter(axisFormatter4);
        lightChart.getAxisLeft().setTextColor(Color.LTGRAY);
        lightChart.getAxisLeft().setTextSize(12);
        lightChart.getAxisLeft().setDrawAxisLine(false);
        lightChart.getAxisLeft().setLabelCount(6);
        lightChart.setExtraTopOffset(10);
        Legend legend4 = lightChart.getLegend();
        legend4.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend4.setForm(Legend.LegendForm.CIRCLE);
        legend4.setTextColor(Color.LTGRAY);
        legend4.setTextSize(12);
        YAxis right4 = lightChart.getAxisRight();
        right4.setDrawLabels(false); // no axis labels
        right4.setDrawGridLines(false);
        right4.setAxisMaximum(28);
        right4.setDrawAxisLine(false); // no axis line
        YAxis left4 = lightChart.getAxisLeft();
        left4.setValueFormatter(new LargeValueFormatter());
        XAxis xAxis4 = lightChart.getXAxis();
        xAxis4.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis4.setDrawAxisLine(true);
        xAxis4.setDrawGridLines(false);
        xAxis4.setLabelCount(5, false);
        xAxis4.setTextColor(Color.LTGRAY);
        xAxis4.setTextSize(10);

        yVals1 = new ArrayList<>();
        descriptiveStatistics = new DescriptiveStatistics();
        for (int i = 0; i < keyLightNoiseArray.get(index).size(); i++) {
            float val = (float) keyLightNoiseArray.get(index).get(i).getVitalsData().getNoise();
            yVals1.add(new Entry(i, val));
            descriptiveStatistics.addValue(val);
        }
        max = (int) descriptiveStatistics.getMax();
        min = (int) descriptiveStatistics.getMin();
        avg = (int) descriptiveStatistics.getMean();
        TextView noise_max = (TextView) view.findViewById(R.id.noise_max);
        noise_max.setText(String.valueOf(max));
        TextView noise_min = (TextView) view.findViewById(R.id.noise_min);
        noise_min.setText(String.valueOf(min));
        TextView noise_avg = (TextView) view.findViewById(R.id.noise_avg);
        noise_avg.setText(String.valueOf(avg));


        LineDataSet set5;
        set5 = new LineDataSet(yVals1, "Noise (dB)");
        set5.setColor(Color.GREEN);
        set5.setDrawCircles(false);
        set5.setLineWidth(3f);
        set5.setDrawValues(false);
        set5.setHighlightEnabled(false);
        set5.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set5.setCubicIntensity(0.1f);
        LineData data5 = new LineData(set5);
        MinutesAxisValueFormatter axisFormatter5 = new MinutesAxisValueFormatter(keyLightNoiseArray.get(index).get(0).getTime());
        noiseChart.setData(data5);
        noiseChart.getDescription().setEnabled(false);
        noiseChart.getXAxis().setValueFormatter(axisFormatter5);
        noiseChart.getAxisLeft().setTextColor(Color.LTGRAY);
        noiseChart.getAxisLeft().setTextSize(12);
        noiseChart.getAxisLeft().setDrawAxisLine(false);
        noiseChart.getAxisLeft().setLabelCount(6);
        noiseChart.setExtraTopOffset(10);
        Legend legend5 = noiseChart.getLegend();
        legend5.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend5.setForm(Legend.LegendForm.CIRCLE);
        legend5.setTextColor(Color.LTGRAY);
        legend5.setTextSize(12);
        YAxis right5 = noiseChart.getAxisRight();
        right5.setDrawLabels(false); // no axis labels
        right5.setDrawGridLines(false);
        right5.setAxisMaximum(28);
        right5.setDrawAxisLine(false); // no axis line
        XAxis xAxis5 = noiseChart.getXAxis();
        xAxis5.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis5.setDrawAxisLine(true);
        xAxis5.setDrawGridLines(false);
        xAxis5.setLabelCount(5, false);
        xAxis5.setTextColor(Color.LTGRAY);
        xAxis5.setTextSize(10);
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
                        }

                        updateFragment(getView());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void getLightNoiseData() {
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

        keyLightNoiseArray.add(new ArrayList<>());
        mDatabaseReference
                .child("vitals_data")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            KeyVitalsData keyLightNoise = new KeyVitalsData();
                            keyLightNoise.setTime(Integer.parseInt(ds.getKey()));
                            keyLightNoise.setVitalsData(ds.getValue(VitalsData.class));
                            keyLightNoiseArray.get(currentPage).add(keyLightNoise);
                            Log.d(TAG, "keyLightNoise: " + keyLightNoise.getVitalsData().toString());
                        }

                        updateLightNoise(getView());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private boolean isVisible;
    private boolean isStarted;

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible) {

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

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
        isVisible = isVisibleToUser;

        if (isVisible && isStarted) {
            for (int i = 0; i < keyEnvArray.get(0).size(); i++) {
                Log.d(TAG, "Getting Data for currentPage: " + currentPage);
                Log.d(TAG, "on Current page Calender Day is: " + calendar.getTime());
                Log.d(TAG, "Timestamp: " + keyEnvArray.get(currentPage).get(i).getTime() + "        keyEnvData Fragment: " + keyEnvArray.get(0).get(i).getEnvironmentalData().toString());
            }
        }
    }

    private void prepareData() {
        Timeline timeline = new Timeline("11:15PM", "23ºC", "45%", "453lx", "34dB", "45ppm");
        timeline.setTimelineText("Heater was on and Temperature got stabilized as well Humidity. Light level kept decreasing along with Noise level. Air Quality was okay.");
        mTimelineList.add(timeline);

        timeline = new Timeline("11:45PM", "20ºC", "34%", "43lx", "21dB", "44ppm");
        timeline.setTimelineText("Room Temperature got stabilized and Humidity reaches its maximum, heater turned off. Lights switched off and you slept in 15min.");
        mTimelineList.add(timeline);

        timeline = new Timeline("12:30AM", "18ºC", "33%", "20lx", "19dB", "41ppm");
        timeline.setTimelineText("Temperature, Humidity, Noise Level kept decreasing since last 2 hours, a little fluctuation detected in light.");
        mTimelineList.add(timeline);

        timeline = new Timeline("03:10AM", "16ºC", "43%", "20lx", "21dB", "39ppm");
        timeline.setTimelineText("Temperature started to fall a little and humidity, noise level also kept decreasing. Air quality improved and it was much improved as compared to yesterday.");
        mTimelineList.add(timeline);

        timeline = new Timeline("05:30AM", "15ºC", "46%", "23lx", "25dB", "31ppm");
        timeline.setTimelineText("Sun rises and light level started to increase. Sleep Therapy was set in music and sun rise mode. Artificial light started to spread in your room.");
        mTimelineList.add(timeline);

        timeline = new Timeline("07:15AM", "21ºC", "51%", "223lx", "39dB", "33ppm");
        timeline.setTimelineText("Good Morning. You had really good sleep tonight. Your home environment is perfect to give good night sleep. Auto energy saver of SleepDoc+ has saved 33 unit of energy by controlling bedroom AC");
        mTimelineList.add(timeline);

        mAdapter.notifyDataSetChanged();
    }

    private void setData(int count, int range) {


    }

//    @Override
//    public void onBackPressed() {
//        if (mLayout != null &&
//                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
//            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//        } else {
//            super.onBackPressed();
//        }
//    }


    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues[(int) value];
        }
    }
}