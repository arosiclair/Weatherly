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

import java.util.Calendar;
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

        //Retrieve today's forecast
        FIODaily today = new FIODaily(mFIO);
        todaysForecast.setCondition(today.getDay(0).summary());
        todaysForecast.setLowTemp((int) (today.getDay(0).temperatureMin() + 0.5));
        todaysForecast.setHighTemp((int) (today.getDay(0).temperatureMax() + 0.5));
        findPrecipChances(mFIO, todaysForecast);
        findWindSpeeds(mFIO, todaysForecast);
        findHumidities(mFIO, todaysForecast);
    }

    private void findPrecipChances(ForecastIO FIO, WeatherlyDayForecast day){
        //Extract hourly forecast data
        FIOHourly hourlyForecast = new FIOHourly(FIO);

        //Determine the number of hours left in the day
        Calendar rightNow = Calendar.getInstance();
        int dayHoursLeft = 24 - rightNow.get(Calendar.HOUR_OF_DAY);

        //Find the lowest and highest chances for precipitation for the rest of the day
        double minChance = hourlyForecast.getHour(0).precipProbability();
        double maxChance = minChance;
        int maxHour = rightNow.get(Calendar.HOUR_OF_DAY);
        for (int i = 1; i < dayHoursLeft; i++){
            double chance = hourlyForecast.getHour(i).precipProbability();
            if (minChance > chance)
                minChance = chance;
            if (maxChance < chance) {
                maxChance = chance;
                maxHour = i + rightNow.get(Calendar.HOUR_OF_DAY);
            }

        }

        day.setMinChanceOfPrecip((int) (minChance * 100));
        day.setMaxChanceOfPrecip((int) (maxChance * 100));
        day.setMaxChanceTime(maxHour);
    }

    private void findWindSpeeds(ForecastIO FIO, WeatherlyDayForecast day){
        //Extract hourly forecast data
        FIOHourly hourlyForecast = new FIOHourly(FIO);

        //Determine the number of hours left in the day
        Calendar rightNow = Calendar.getInstance();
        int dayHoursLeft = 24 - rightNow.get(Calendar.HOUR_OF_DAY);

        //Find the lowest and highest chances for precipitation for the rest of the day
        double minSpeed = hourlyForecast.getHour(0).windSpeed();
        double maxSpeed = minSpeed;
        int maxHour = rightNow.get(Calendar.HOUR_OF_DAY);
        for (int i = 1; i < dayHoursLeft; i++){
            double speed = hourlyForecast.getHour(i).windSpeed();
            if (minSpeed > speed)
                minSpeed = speed;
            if (maxSpeed < speed) {
                maxSpeed = speed;
                maxHour = i + rightNow.get(Calendar.HOUR_OF_DAY);
            }

        }

        day.setWindMin((int) (minSpeed + 0.5));
        day.setWindMax((int) (maxSpeed + 0.5));
        day.setWindMaxTime(maxHour);
    }

    private void findHumidities(ForecastIO FIO, WeatherlyDayForecast day){
        //Extract hourly forecast data
        FIOHourly hourlyForecast = new FIOHourly(FIO);
        int numHours = hourlyForecast.hours();

        //Determine the number of hours left in the day
        Calendar rightNow = Calendar.getInstance();
        int dayHoursLeft = 24 - rightNow.get(Calendar.HOUR_OF_DAY);

        //Find the lowest and highest chances for precipitation for the rest of the day
        double minHum = hourlyForecast.getHour(0).humidity();
        double maxHum = minHum;
        int maxHour = rightNow.get(Calendar.HOUR_OF_DAY);
        for (int i = 1; i < dayHoursLeft; i++){
            double hum = hourlyForecast.getHour(i).humidity();
            if (minHum > hum)
                minHum = hum;
            if (maxHum < hum) {
                maxHum = hum;
                maxHour = i + rightNow.get(Calendar.HOUR_OF_DAY);
            }

        }

        day.setHumMin((int) (minHum * 100));
        day.setHumMax((int) (maxHum * 100));
        day.setHumMaxTime(maxHour);
    }
}
