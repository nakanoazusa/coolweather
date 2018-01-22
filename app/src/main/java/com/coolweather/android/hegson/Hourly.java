package com.coolweather.android.hegson;

/**
 * Created by sc on 2018/1/18.
 */

public class Hourly {
    public String time; //预报时间，格式yyyy-MM-dd hh:mm
    public String tmp; //温度
    public String cond_code; //实况天气状况代码
    public String cond_txt; //实况天气状况代码
    public String wind_deg; //风向360角度
    public String wind_dir; //风向
    public String wind_sc; //风力
    public String wind_spd; //风速，公里/小时
    public String hum; //相对湿度
    public String pres; //大气压强
    public String vis; //能见度，默认单位：公里
    public String cloud; //云量

}
