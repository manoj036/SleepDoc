package com.example.manojkumar.practiceui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DatabaseReference;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by mBreath on 15-04-2018.
 */

public class RespirationFragment extends Fragment {

    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "Date Fragment";
    private DatabaseReference mDatabaseReference;
    private static ArrayList<ArrayList<KeyVitalsData>> keyVitalsDataArrayFrag = new ArrayList<ArrayList<KeyVitalsData>>();
    private int mPage;
    View mView;

    private LineChart mRespirationChart;

    public static RespirationFragment newInstance(int page, ArrayList<ArrayList<KeyVitalsData>> vitalsDataArray) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RespirationFragment fragment = new RespirationFragment();
        fragment.setArguments(args);
        keyVitalsDataArrayFrag = vitalsDataArray;
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
            updateRespiration(mView);
        }
        return mView;
    }


    private void updateRespiration(View view) {
        int index;
        index = 6 - mPage;
        /***
         * Setting Respiration Plot
         */
        ArrayList<Entry> yVals1 = new ArrayList<>();
        //Used Jugaad to overcome empty dataset plot
        yVals1.add(new Entry(0, 12));
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        if (keyVitalsDataArrayFrag.get(index).size() != 0) {
            for (int i = 0; i < keyVitalsDataArrayFrag.get(index).size(); i++) {
                float val = (float) keyVitalsDataArrayFrag.get(index).get(i).getVitalsData().getResp_rate();
                Log.d(TAG, "updateRespiration: For Loop" + val);
                yVals1.add(new Entry(i, val));
                descriptiveStatistics.addValue(val);
            }
        }

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
        MinutesAxisValueFormatter axisFormatter4;
        try {
            axisFormatter4 = new MinutesAxisValueFormatter(keyVitalsDataArrayFrag.get(index).get(0).getTime());
        } catch (Exception e) {
            axisFormatter4 = new MinutesAxisValueFormatter(899898);
        }
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
        resp_avg_pb.setProgress(avg);
        TextView resp_var = (TextView) view.findViewById(R.id.var_respiration);
        resp_var.setText(String.valueOf(std));
        ProgressBar resp_var_pb = (ProgressBar) view.findViewById(R.id.pb_var_respiration);
		resp_var_pb.setProgress(std);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                resp_max_pb.setProgress(max);
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                resp_min_pb.setProgress(min);
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                resp_avg_pb.setProgress(avg);
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                resp_var_pb.setProgress(std);
            }
        });
    }
}