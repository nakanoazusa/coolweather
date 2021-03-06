package com.coolweather.android.hegson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sc on 2018/1/18.
 */

public class Forecast {
    public String date; //预报日期
    @SerializedName("sr")
    public String sunrise; //日出时间
    @SerializedName("ss")
    public String sunset; //日落时间
    @SerializedName("mr")
    public String moonrise; //月升时间
    @SerializedName("ms")
    public String moonset; //月落时间
    public String tmp_max; //最高温度
    public String tmp_min; //最低温度
    public String cond_code_d; //白天天气状况代码
    public String cond_code_n; //晚间天气状况代码
    public String cond_txt_d; //白天天气状况描述
    public String cond_txt_n; //晚间天气状况描述
    public String wind_deg; //风向360角度
    public String wind_dir; //风向
    public String wind_sc; //风力
    public String wind_spd; //风速，公里/小时
    public String hum; //相对湿度
    public String pcpn; //降水量
    public String pop; //降水概率
    public String pres; //大气压强
    public String uv_index; //紫外线强度指数
    public String vis; //能见度，单位：公里
}

