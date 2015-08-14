package com.rosiclair.andrew.weatherly.data;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by arosi on 8/13/2015.
 */
public class WeatherlyDataModel {

    //The collection of WeatherlyCities that will be used to update the views
    private WeatherlyCity currentLocation;
    private ArrayList<WeatherlyCity> cities;

    public WeatherlyDataModel(){
        currentLocation = new WeatherlyCity();
        cities = new ArrayList<>();
    }

    public WeatherlyCity getCurrentLocation() {
        return currentLocation;
    }

    public ArrayList<WeatherlyCity> getCities() {
        return cities;
    }

    public void updateCurrentLocation(Location lastKnown){

    }
}
