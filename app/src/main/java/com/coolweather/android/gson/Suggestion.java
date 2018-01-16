package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sc on 2018/1/16.
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfore comfore;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfore{
        @SerializedName("txt")
        public String info;
    }

    public class CarWash{
        @SerializedName("txt")
        public String info;
    }

    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
