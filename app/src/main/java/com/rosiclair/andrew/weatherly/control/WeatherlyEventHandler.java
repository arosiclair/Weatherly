package com.rosiclair.andrew.weatherly.control;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.rosiclair.andrew.weatherly.data.WeatherlyCity;
import com.rosiclair.andrew.weatherly.data.WeatherlyDataModel;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;

/**
 * Created by arosi on 8/13/2015.
 */
public class WeatherlyEventHandler {

    private AppCompatActivity mActivity;

    private WeatherClient mWeatherClient;
    private WeatherlyDataModel mDataModel;

    public WeatherlyEventHandler(AppCompatActivity activity, WeatherlyDataModel dataModel){
        mActivity = activity;
        mDataModel = dataModel;
    }

    public void onLocationUpdate(Location lastKnown){

    }

    public void buildWeatherClient(){
        WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();

        //Init and setup config
        WeatherConfig config = new WeatherConfig();
        config.ApiKey = "db1ce9e4cc58d1f6f010471c33d68479";
        config.unitSystem = WeatherConfig.UNIT_SYSTEM.I;
        config.lang = "en";
        config.maxResult = 5; // Max number of cities retrieved
        config.numDays = 1; // Max num of days in the forecast

        try {
            mWeatherClient = builder.attach(mActivity)
                    .provider(new OpenweathermapProviderType())
                    .httpClient(com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault.class)
                    .config(config)
                    .build();
        } catch (WeatherProviderInstantiationException e) {
            e.printStackTrace();
        }
    }
}
