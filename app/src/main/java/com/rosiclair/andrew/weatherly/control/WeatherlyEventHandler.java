package com.rosiclair.andrew.weatherly.control;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.FIOHourly;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import com.rosiclair.andrew.weatherly.data.WeatherlyCity;
import com.rosiclair.andrew.weatherly.data.WeatherlyDataModel;
import com.rosiclair.andrew.weatherly.data.WeatherlyDayForecast;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * An Event Handler that handles all UI and refresh events
 */
public class WeatherlyEventHandler {

    private AppCompatActivity mActivity;
    private WeatherlyDataModel mDataModel;

    private ForecastIO mFIO;

    public WeatherlyEventHandler(AppCompatActivity activity, WeatherlyDataModel dataModel){
        mActivity = activity;
        mDataModel = dataModel;
    }

    public void onLocationUpdate(Location lastKnown){
        double latitude = lastKnown.getLatitude();
        double longitude = lastKnown.getLongitude();
        ForecastIOWorkerTask fioTask;

        //Initialize a new ForecastIO object and retrieve the forecast data
        mFIO = new ForecastIO("147011b1efd5b4f5db2beafa790a82b6");
        mFIO.setUnits(ForecastIO.UNITS_US);
        mFIO.setExcludeURL("minutely");
        new ForecastIOWorkerTask(this, mFIO, latitude, longitude).execute(mFIO, latitude, longitude);
    }

    public void onFIODataRetrieve(){
        WeatherlyCity currentLocation = mDataModel.getCurrentLocation();
        WeatherlyDayForecast todaysForecast = currentLocation.getTodaysForecast();

        //Retrieve current conditions
        FIOCurrently currently = new FIOCurrently(mFIO);
        currentLocation.setCurrentCondition(currently.get().summary());
        currentLocation.setCurrentTemp((int) (currently.get().temperature() + 0.5));
        currentLocation.setFeelsLike((int) (currently.get().apparentTemperature() + 0.5));
        currentLocation.setWind((int) (currently.get().windSpeed() + 0.5));
        currentLocation.setHumidity((int) (currently.get().humidity() * 100));
        currentLocation.setVisibility((int) (currently.get().visibility() + 0.5));

        //TODO Retrieve today's forecast
        FIODaily today = new FIODaily(mFIO);
        int num = today.days();
        todaysForecast.setLowTemp((int) (today.getDay(0).temperatureMin() + 0.5));
        todaysForecast.setHighTemp((int) (today.getDay(0).temperatureMax() + 0.5));
        findPrecipChances(mFIO, todaysForecast);
    }

    private void findPrecipChances(ForecastIO FIO, WeatherlyDayForecast day){
        int numHours;

        FIOHourly hourlyForecast = new FIOHourly(FIO);
        numHours = hourlyForecast.hours();
    }
}
