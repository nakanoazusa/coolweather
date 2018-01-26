package com.coolweather.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coolweather.android.hegson.Hourly;

import java.util.List;

/**
 * Created by sc on 2018/1/26.
 */

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    private List<Hourly> hourlyList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tmp;
        TextView condTxt;
        TextView windInfo;
        TextView hourlTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tmp = itemView.findViewById(R.id.tmp);
            condTxt = itemView.findViewById(R.id.cond_txt);
            windInfo = itemView.findViewById(R.id.wind_info);
            hourlTime = itemView.findViewById(R.id.hourl_time);
        }
    }

    public HourlyAdapter(List<Hourly> hourlyList) {
        this.hourlyList = hourlyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hourly hourly = hourlyList.get(position);
        holder.tmp.setText(hourly.tmp + "℃");
        holder.condTxt.setText(hourly.cond_txt);
        holder.windInfo.setText(hourly.wind_dir + hourly.wind_sc + "级");
        holder.hourlTime.setText(hourly.time.split(" ")[1]);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }


}
