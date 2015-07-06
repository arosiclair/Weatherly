package com.rosiclair.andrew.weatherly.data;

import java.util.ArrayList;

/**
 * Created by Andrew on 7/5/2015.
 */
public class WeatherlyDayForecast {

    //The day's weather details.
    private int lowTemp;
    private int highTemp;
    private int minChanceOfPrecip;
    private int maxChanceOfPrecip;
    private int maxChanceTime;
    private int windMin;
    private int windMax;
    private int windMaxTime;
    private int humMin;
    private int humMax;
    private int humMaxTime;
    private String condition;

    // The list of hourly forecasts for the day.
    private ArrayList<WeatherlyHourForecast> hourlyForecasts;

    //Accessors
    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public int getMinChanceOfPrecip() {
        return minChanceOfPrecip;
    }

    public void setMinChanceOfPrecip(int minChanceOfPrecip) {
        this.minChanceOfPrecip = minChanceOfPrecip;
    }

    public int getMaxChanceOfPrecip() {
        return maxChanceOfPrecip;
    }

    public void setMaxChanceOfPrecip(int maxChanceOfPrecip) {
        this.maxChanceOfPrecip = maxChanceOfPrecip;
    }

    public int getMaxChanceTime() {
        return maxChanceTime;
    }

    public void setMaxChanceTime(int maxChanceTime) {
        this.maxChanceTime = maxChanceTime;
    }

    public int getWindMin() {
        return windMin;
    }

    public void setWindMin(int windMin) {
        this.windMin = windMin;
    }

    public int getWindMax() {
        return windMax;
    }

    public void setWindMax(int windMax) {
        this.windMax = windMax;
    }

    public int getWindMaxTime() {
        return windMaxTime;
    }

    public void setWindMaxTime(int windMaxTime) {
        this.windMaxTime = windMaxTime;
    }

    public int getHumMin() {
        return humMin;
    }

    public void setHumMin(int humMin) {
        this.humMin = humMin;
    }

    public int getHumMax() {
        return humMax;
    }

    public void setHumMax(int humMax) {
        this.humMax = humMax;
    }

    public int getHumMaxTime() {
        return humMaxTime;
    }

    public void setHumMaxTime(int humMaxTime) {
        this.humMaxTime = humMaxTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public ArrayList<WeatherlyHourForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public void setHourlyForecasts(ArrayList<WeatherlyHourForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts;
    }
}
