package com.coolweather.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolweather.android.hegson.Forecast;
import com.coolweather.android.hegson.HeWeather;
import com.coolweather.android.hegson.Hourly;
import com.coolweather.android.hegson.Lifestyle;
import com.coolweather.android.service.AutoUpdateService;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.MyApplication;
import com.coolweather.android.util.ResourcesUtils;
import com.coolweather.android.util.Utility;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HeWeatherActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    //界面控件
    private ImageView weatherImage;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView wind_dir;
    private TextView wind_sc;
    private TextView hum;
    private TextView fl;
    private LinearLayout forecastLayout;
    private RecyclerView hourlyEecyclerView;
    private HourlyAdapter adapter;
    private TextView chuanyiBrf;
    private TextView chuanyiTxt;
    private TextView spfBrf;
    private TextView spfTxt;
    private TextView sportBrf;
    private TextView sportTxt;
    private TextView cwBrf;
    private TextView cwTxt;

    private String mWeatherId;
    private String mWeatherUpDateTime;
    public DrawerLayout drawerLayout;
    private Button navButton;
    public RefreshLayout refreshLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String toolbarInfo;
    private List<Hourly> hourlyList = new ArrayList<>();
    private MiuiWeatherView weatherView;
    private HorizontalScrollView hsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_he_weather);
        weatherImage = findViewById(R.id.weather_image);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        wind_dir = findViewById(R.id.wind_dir);
        wind_sc = findViewById(R.id.wind_sc);
        hum = findViewById(R.id.hum);
        fl = findViewById(R.id.fl);
        chuanyiBrf = findViewById(R.id.chuanyi_brf);
        chuanyiTxt = findViewById(R.id.chuanyi_txt);
        spfBrf = findViewById(R.id.spf_brf);
        spfTxt = findViewById(R.id.spf_txt);
        sportBrf = findViewById(R.id.sport_brf);
        sportTxt = findViewById(R.id.sport_txt);
        cwBrf = findViewById(R.id.cw_brf);
        cwTxt = findViewById(R.id.cw_txt);
        forecastLayout = findViewById(R.id.forecast_layout);
        /*hourlyEecyclerView = findViewById(R.id.hourly_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hourlyEecyclerView.setLayoutManager(layoutManager);
        hourlyList.add(new Hourly());
        adapter = new HourlyAdapter(hourlyList);
        hourlyEecyclerView.setAdapter(adapter);
        hourlyEecyclerView.setNestedScrollingEnabled(false);*/
        hsv = findViewById(R.id.hsv);
        weatherView = findViewById(R.id.weather);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hsv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    weatherView.setmScrollX(scrollX);
                    weatherView.invalidate();
                }
            });
        } else {
            weatherView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    weatherView.setmScrollX(hsv.getScrollX());
                    weatherView.invalidate();
                }
            });
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navButton = findViewById(R.id.nav_button);
        appBarLayout = findViewById(R.id.appbar_layout);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        refreshLayout  = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));//设置Header
        //swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            HeWeather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.cityId;
            mWeatherUpDateTime = weather.update.loc;
            showWeatherInfo(weather);
        } else {
            mWeatherId = getIntent().getStringExtra("weather_id");
            //weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestWeather(mWeatherId);
            }
        });
        /*String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(weatherImage);
        } else {
            loadBingPic();
        }*/
        Glide.with(this).load(R.drawable.weather_img_1).into(weatherImage);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * 根据天气ID获取城市天气信息
     * @param weatherId
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = "https://free-api.heweather.com/s6/weather?location=" + weatherId + "&key=3340d18f8777464c89463322d605c1b0";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HeWeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishRefresh();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final HeWeather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(HeWeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.cityId;
                            mWeatherUpDateTime = weather.update.loc;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(HeWeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        refreshLayout.finishRefresh();
                    }
                });
            }
        });
        loadBingPic();
    }

    private void showWeatherInfo(HeWeather weather) {
        toolbarInfo = weather.basic.cityName + " " + weather.now.cond_txt + " " + weather.now.tmp + "℃";
        String cityName = weather.basic.cityName;
        String updateTime = weather.update.loc.split(" ")[1];
        String degree = weather.now.tmp;
        String weatherInfo = weather.now.cond_txt;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        wind_dir.setText(weather.now.wind_dir);
        wind_sc.setText(weather.now.wind_sc.equals("微风") ? weather.now.wind_sc : weather.now.wind_sc + "级");
        hum.setText(weather.now.hum + "%");
        fl.setText(weather.now.feeltmp + "℃");
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView tmp = view.findViewById(R.id.tmp);
            ImageView weatherImg = view.findViewById(R.id.weather_img);
            dateText.setText(forecast.date);
            infoText.setText(forecast.cond_txt_d);
            //weatherImg.setImageResource(ResourcesUtils.getDrawableId(MyApplication.getContext(),"hw" + forecast.cond_code_d));
            Glide.with(this).load(ResourcesUtils.getDrawableId(MyApplication.getContext(),"hw" + forecast.cond_code_d)).into(weatherImg);
            tmp.setText(forecast.tmp_max + " / " + forecast.tmp_min + "℃");
            forecastLayout.addView(view);
        }
        hourlyList.clear();
        for (Hourly hourly : weather.hourlyList) {
            hourlyList.add(new Hourly(hourly.time.split(" ")[1], hourly.tmp, hourly.cond_code, hourly.cond_txt));
        }
        weatherView.setData(hourlyList);
        weatherView.scrollTo(0, 0);
        //adapter.notifyDataSetChanged();
        //hourlyEecyclerView.smoothScrollToPosition(0);

        /*if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }*/

        for (Lifestyle lifestyle : weather.lifestyleList) {
            if (lifestyle.type.equals("drsg")) {
                chuanyiBrf.setText(lifestyle.brf);
                chuanyiTxt.setText(lifestyle.txt);
            }
            if (lifestyle.type.equals("uv")) {
                spfBrf.setText(lifestyle.brf);
                spfTxt.setText(lifestyle.txt);
            }
            if (lifestyle.type.equals("sport")) {
                sportBrf.setText(lifestyle.brf);
                sportTxt.setText(lifestyle.txt);
            }
            if (lifestyle.type.equals("cw")) {
                cwBrf.setText(lifestyle.brf);
                cwTxt.setText(lifestyle.txt);
            }
        }
        //weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(HeWeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Glide.with(HeWeatherActivity.this).load(bingPic).into(weatherImage);
                        Glide.with(HeWeatherActivity.this).load(R.drawable.weather_img_1).into(weatherImage);
                    }
                });
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            collapsingToolbarLayout.setTitle("");
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            collapsingToolbarLayout.setTitle(toolbarInfo);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = this.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(this.getResources().getColor(R.color.mDownBarTextColor));
                    window.setNavigationBarColor(this.getResources().getColor(R.color.mDownBarTextColor));
                }
            }
        } else {
            if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() <= -(appBarLayout.getTotalScrollRange()*0.1434)) {
                collapsingToolbarLayout.setTitle("");
                if (Build.VERSION.SDK_INT >= 21) {
                    View decorView = getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
            } else {
                collapsingToolbarLayout.setTitle(toolbarInfo);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.getWindow().getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = this.getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(this.getResources().getColor(R.color.mDownBarTextColor));
                        window.setNavigationBarColor(this.getResources().getColor(R.color.mDownBarTextColor));
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
        updateWeather();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    private void updateWeather() {
        try {
            Date curDate = new Date(System.currentTimeMillis());
            Date oldDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mWeatherUpDateTime);
            if (((curDate.getTime() - oldDate.getTime()) / (1 * 60 * 60 * 1000)) >= 1) {
                requestWeather(mWeatherId);
            }
        } catch (Exception e) {
        }
    }
}
