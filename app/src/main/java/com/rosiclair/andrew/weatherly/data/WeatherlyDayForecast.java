package com.rosiclair.andrew.weatherly.data;

import java.util.ArrayList;

/**
 * Created by Andrew on 7/5/2015.
 */
public class WeatherlyDayForecast {

    //The day's weather details.
    private int mLowTemp;
    private int mHighTemp;
    private int mMinChanceOfPrecip;
    private int mMaxChanceOfPrecip;
    private int mMaxChanceTime;
    private int mWindMin;
    private int mWindMax;
    private int mWindMaxTime;
    private int mHumMin;
    private int mHumMax;
    private int mHumMaxTime;
    private String mCondition;

    // The list of hourly forecasts for the day.
    private ArrayList<WeatherlyHourForecast> hourlyForecasts;

    //Accessors
    public int getLowTemp() {
        return mLowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.mLowTemp = lowTemp;
    }

    public int getHighTemp() {
        return mHighTemp;
    }

    public void setHighTemp(int highTemp) {
        this.mHighTemp = highTemp;
    }

    public int getMinChanceOfPrecip() {
        return mMinChanceOfPrecip;
    }

    public void setMinChanceOfPrecip(int minChanceOfPrecip) {
        this.mMinChanceOfPrecip = minChanceOfPrecip;
    }

    public int getMaxChanceOfPrecip() {
        return mMaxChanceOfPrecip;
    }

    public void setMaxChanceOfPrecip(int maxChanceOfPrecip) {
        this.mMaxChanceOfPrecip = maxChanceOfPrecip;
    }

    public int getMaxChanceTime() {
        return mMaxChanceTime;
    }

    public void setMaxChanceTime(int maxChanceTime) {
        this.mMaxChanceTime = maxChanceTime;
    }

    public int getWindMin() {
        return mWindMin;
    }

    public void setWindMin(int windMin) {
        this.mWindMin = windMin;
    }

    public int getWindMax() {
        return mWindMax;
    }

    public void setWindMax(int windMax) {
        this.mWindMax = windMax;
    }

    public int getWindMaxTime() {
        return mWindMaxTime;
    }

    public void setWindMaxTime(int windMaxTime) {
        this.mWindMaxTime = windMaxTime;
    }

    public int getHumMin() {
        return mHumMin;
    }

    public void setHumMin(int humMin) {
        this.mHumMin = humMin;
    }

    public int getHumMax() {
        return mHumMax;
    }

    public void setHumMax(int humMax) {
        this.mHumMax = humMax;
    }

    public int getHumMaxTime() {
        return mHumMaxTime;
    }

    public void setHumMaxTime(int humMaxTime) {
        this.mHumMaxTime = humMaxTime;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String condition) {
        this.mCondition = condition;
    }

    public ArrayList<WeatherlyHourForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public void setHourlyForecasts(ArrayList<WeatherlyHourForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts;
    }
}
