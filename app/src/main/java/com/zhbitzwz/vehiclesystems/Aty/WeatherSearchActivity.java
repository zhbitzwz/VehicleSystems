package com.zhbitzwz.vehiclesystems.Aty;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.zhbitzwz.vehiclesystems.R;
import com.zhbitzwz.vehiclesystems.Util.ToastUtil;

import java.util.List;

public class WeatherSearchActivity extends AppCompatActivity implements WeatherSearch.OnWeatherSearchListener{

    private  Toolbar toolbar;
    private TextView forecasttv;
    private TextView reporttime1;
    private TextView reporttime2;
    private TextView weather;
    private TextView Temperature;
    private TextView wind;
    private TextView humidity;
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;
    private LocalWeatherForecast weatherforecast;
    private List<LocalDayWeatherForecast> forecastlist = null;
    private String cityname="北京市";//天气搜索的城市，可以写名称或adcode；

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //天气预报的toolbar
        TypedValue typedValueColorPrimaryDark = new TypedValue();
        WeatherSearchActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        //获取用户输入的城市
        Intent intent = getIntent();
        cityname = intent.getStringExtra("city");

        init();
        searchliveweather();
        searchforecastweather();
    }

    private void searchforecastweather() {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        mweathersearch = new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn();
    }

    private void searchliveweather() {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1 WEATHER_TYPE_LIVE、天气预报为2 WEATHER_TYPE_FORECAST
        mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    private void init() {
        TextView city =(TextView)findViewById(R.id.city);
        city.setText(cityname);
        forecasttv=(TextView)findViewById(R.id.forecast);
        reporttime1 = (TextView)findViewById(R.id.reporttime1);
        reporttime2 = (TextView)findViewById(R.id.reporttime2);
        weather = (TextView)findViewById(R.id.weather);
        Temperature = (TextView)findViewById(R.id.temp);
        wind=(TextView)findViewById(R.id.wind);
        humidity = (TextView)findViewById(R.id.humidity);
    }

    /**
     * 实时天气查询回调
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000) {
            if (localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null) {
                weatherlive = localWeatherLiveResult.getLiveResult();
                reporttime1.setText(weatherlive.getReportTime()+"发布");
                weather.setText(weatherlive.getWeather());
                Temperature.setText(weatherlive.getTemperature()+"°");
                wind.setText(weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级");
                humidity.setText("湿度         "+weatherlive.getHumidity()+"%");
                ToastUtil.show(WeatherSearchActivity.this,"发布：" + weatherlive.getReportTime());
            } else  {
                ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
            }
        } else  {
            ToastUtil.showerror(WeatherSearchActivity.this, i);
        }
    }

    /**
     * 天气预报查询结果回调
     * */
    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
        if (i == 1000) {
            if (localWeatherForecastResult != null && localWeatherForecastResult.getForecastResult() != null
                    && localWeatherForecastResult.getForecastResult().getWeatherForecast() != null
                    && localWeatherForecastResult.getForecastResult().getWeatherForecast().size() > 0) {
                forecastlist = (List<LocalDayWeatherForecast>) localWeatherForecastResult.getWeatherForecastQuery();
                fillforecast();
            } else {
                ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
            }
        } else  {
            ToastUtil.showerror(WeatherSearchActivity.this, i);
        }
    }

    private void fillforecast() {
        reporttime2.setText(weatherforecast.getReportTime()+"发布");
        String forecast="";
        for (int i = 0; i < forecastlist.size(); i++) {
            LocalDayWeatherForecast localdayweatherforecast=forecastlist.get(i);
            String week = null;
            switch (Integer.valueOf(localdayweatherforecast.getWeek())) {
                case 1:
                    week = "周一";
                    break;
                case 2:
                    week = "周二";
                    break;
                case 3:
                    week = "周三";
                    break;
                case 4:
                    week = "周四";
                    break;
                case 5:
                    week = "周五";
                    break;
                case 6:
                    week = "周六";
                    break;
                case 7:
                    week = "周日";
                    break;
                default:
                    break;
            }
            String temp =String.format("%-3s/%3s",
                    localdayweatherforecast.getDayTemp()+"°",
                    localdayweatherforecast.getNightTemp()+"°");
            String date = localdayweatherforecast.getDate();
            forecast+=date+"  "+week+"                       "+temp+"\n\n";
        }
        forecasttv.setText(forecast);
    }
}
