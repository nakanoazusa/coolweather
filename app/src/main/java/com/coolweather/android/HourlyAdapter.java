package com.coolweather.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coolweather.android.hegson.Hourly;
import com.coolweather.android.util.MyApplication;
import com.coolweather.android.util.ResourcesUtils;

import java.util.List;

/**
 * Created by sc on 2018/1/26.
 */

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    private List<Hourly> hourlyList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tmp;
        ImageView condImg;
        TextView windInfo;
        TextView hourlTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tmp = itemView.findViewById(R.id.tmp);
            condImg = itemView.findViewById(R.id.cond_img);
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
        holder.windInfo.setText(hourly.wind_dir + hourly.wind_sc + "级");
        Glide.with(MyApplication.getContext()).load(ResourcesUtils.getDrawableId(MyApplication.getContext(),"hw" + hourly.cond_code)).into(holder.condImg);
        holder.hourlTime.setText(hourly.time.split(" ")[1]);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }


}
