package com.coolweather.android.hegson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sc on 2018/1/18.
 */

public class Basic {
    @SerializedName("location")
    public String cityName; //地区／城市名称
    @SerializedName("cid")
    public String cityId; //地区／城市ID
    @SerializedName("lon")
    public String cityLon; //地区／城市纬度
    @SerializedName("lat")
    public String cityLat; //地区／城市经度
    @SerializedName("parent_city")
    public String cityParent; //该地区／城市的上级城市
    @SerializedName("admin_area")
    public String cityProvince; //该地区／城市所属行政区域
    @SerializedName("cnty")
    public String cityCounty; //该地区／城市所属国家名称
    @SerializedName("tz")
    public String cityTimeZone; //该地区／城市所在时区
}
