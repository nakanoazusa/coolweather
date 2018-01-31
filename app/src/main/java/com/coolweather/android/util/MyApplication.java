package com.coolweather.android.util;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by sc on 2018/1/30.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
