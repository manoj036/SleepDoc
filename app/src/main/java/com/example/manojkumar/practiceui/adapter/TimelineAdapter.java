package com.example.manojkumar.practiceui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manojkumar.practiceui.R;
import com.example.manojkumar.practiceui.model.Timeline;

import java.util.List;

/**
 * Created by mbreath on 01/04/18.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyViewHolder>{

    public List<Timeline> timelineTextList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView timelineDate, timelineStatus, temp, humi, light, noise, aq;

        public MyViewHolder(View itemView) {
            super(itemView);
            timelineDate = (TextView) itemView.findViewById(R.id.timeline_date);
            timelineStatus = (TextView) itemView.findViewById(R.id.timeline_status);
            temp = (TextView) itemView.findViewById(R.id.temp_textview_id);
            humi = (TextView) itemView.findViewById(R.id.humidity_textview_id);
            light = (TextView) itemView.findViewById(R.id.light_textview_id);
            noise = (TextView) itemView.findViewById(R.id.noise_textview_id);
            aq = (TextView) itemView.findViewById(R.id.aq_textview_id);

        }
    }

    public TimelineAdapter(List<Timeline> timelineTextList){
        this.timelineTextList = timelineTextList;
    }

    @Override
    public TimelineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineAdapter.MyViewHolder holder, int position) {
        Timeline timelineText = timelineTextList.get(position);
        holder.timelineDate.setText(timelineText.getDate());
        holder.timelineStatus.setText(timelineText.getTimelineText());
        holder.temp.setText(timelineText.getTemp());
        holder.humi.setText(timelineText.getHumidity());
        holder.light.setText(timelineText.getLight());
        holder.noise.setText(timelineText.getNoise());
        holder.aq.setText(timelineText.getAq());
    }

    @Override
    public int getItemCount() {
        return timelineTextList.size();
    }


}
