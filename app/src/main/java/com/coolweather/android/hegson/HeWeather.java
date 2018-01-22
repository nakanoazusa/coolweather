package com.coolweather.android.hegson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sc on 2018/1/18.
 */

public class HeWeather {
    public String status;
    public Basic basic;
    public Update update;
    public Now now;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    @SerializedName("hourly")
    public List<Hourly> hourlyList;

    @SerializedName("lifestyle")
    public List<Lifestyle> lifestyleList;
}
