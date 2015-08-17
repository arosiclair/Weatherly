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

    public void onLocationUpdate(Location lastKnown) {
        WeatherlyCity currentLocation = mDataModel.getCurrentLocation();
        WeatherlyDayForecast todaysForecast = currentLocation.getTodaysForecast();

        double latitude = lastKnown.getLatitude();
        double longitude = lastKnown.getLongitude();
        ForecastIOWorkerTask fioTask;

        //Initialize a new ForecastIO for each new forecast
        mFIO = new ForecastIO("147011b1efd5b4f5db2beafa790a82b6");
        mFIO.setUnits(ForecastIO.UNITS_US);
        mFIO.setExcludeURL("minutely");
        fioTask = new ForecastIOWorkerTask(mFIO, latitude, longitude);
        fioTask.execute();

        //Retrieve current conditions
        FIOCurrently currently = new FIOCurrently(mFIO);
        currentLocation.setCurrentTemp((int) (currently.get().temperature() + 0.5));
        currentLocation.setFeelsLike((int) (currently.get().apparentTemperature() + 0.5));
        currentLocation.setWind((int) (currently.get().windSpeed() + 0.5));
        currentLocation.setHumidity((int) (currently.get().humidity() + 0.5));
        currentLocation.setVisibility((int) (currently.get().visibility() + 0.5));

        //TODO Retrieve today's forecast
    }
}
