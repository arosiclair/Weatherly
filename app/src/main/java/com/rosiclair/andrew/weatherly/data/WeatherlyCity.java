package com.rosiclair.andrew.weatherly.data;

import java.util.ArrayList;

/**
 * Created by Andrew on 7/5/2015.
 */
public class WeatherlyCity {

    private String name;
    private int cityID;
    private double latitude, longitude;
    private String units;
    private int currentTemp, feelsLike, humidity, visibility;
    private int lastUpdateTime;
    private WeatherlyDayForecast todaysForecast;
    private ArrayList<WeatherlyDayForecast> weeksForecast;

    public String getName() {
        return name;
    }

    public int getCityID() {
        return cityID;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public int getFeelsLike(){
        return feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public void update(){

    }
}
