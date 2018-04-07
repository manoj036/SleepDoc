package com.example.manojkumar.practiceui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manojkumar.practiceui.model.DayData;
import com.example.manojkumar.practiceui.R;

/**
 * Created by Manoj Kumar on 05-03-2018.
 */

public class EnvironmentFragment extends Fragment {

    private static final String ARG_PAGE="ARG_PAGE";
    private static final String TAG = "Date Fragment";
    private static DayData[] days_data;
    private static DayData pdayData,dayData;
    private int mPage;
    View[] mView=new View[7];

    public static EnvironmentFragment newInstance(int page, DayData[] val){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        EnvironmentFragment fragment=new EnvironmentFragment();
        fragment.setArguments(args);
        days_data=val;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage=getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mView[mPage]==null){
        View view=inflater.inflate(R.layout.environment_fragment,container,false);
        dayData=days_data[mPage];
        int diff = -mPage;

        EditFragment(view);
        mView[mPage]=view;
        }
        return mView[mPage];
    }

    private void EditFragment(View view) {
    }
}