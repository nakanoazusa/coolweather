package com.coolweather.android.gson;

/**
 * Created by sc on 2018/1/16.
 */

public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
