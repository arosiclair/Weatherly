package com.rosiclair.andrew.weatherly.data;

import java.util.ArrayList;

/**
 * Object containing all current weather information for a city as well as forecasts for the day
 * and week
 */
public class WeatherlyCity {

    private String name;
    private int cityID;
    private double latitude, longitude;
    private String units;
    private int currentTemp, feelsLike, wind, humidity, visibility;
    private String currentCondition;
    private long lastUpdateTime;
    private WeatherlyDayForecast todaysForecast;
    private ArrayList<WeatherlyDayForecast> weeksForecast;

    public WeatherlyCity(){}

    public void update(){

    }

    public String getName() {
        return name;
    }

    public int getCityID() {
        return cityID;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public int getFeelsLike(){ return feelsLike; }

    public int getWind() { return wind; }

    public int getHumidity() {
        return humidity;
    }

    public int getVisibility() { return visibility; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getCurrentCondition() { return currentCondition; }

    public void setCurrentCondition(String currentCondition) { this.currentCondition = currentCondition; }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public WeatherlyDayForecast getTodaysForecast() {
        return todaysForecast;
    }

    public void setTodaysForecast(WeatherlyDayForecast todaysForecast) {
        this.todaysForecast = todaysForecast;
    }

    public ArrayList<WeatherlyDayForecast> getWeeksForecast() {
        return weeksForecast;
    }

    public void setWeeksForecast(ArrayList<WeatherlyDayForecast> weeksForecast) {
        this.weeksForecast = weeksForecast;
    }
}
