package com.rosiclair.andrew.weatherly.control;

import android.os.AsyncTask;

import com.github.dvdme.ForecastIOLib.ForecastIO;

/**
 * An Asynchronous task for pulling ForecastIO data
 */
public class ForecastIOWorkerTask extends AsyncTask {

    ForecastIO mFIO;
    double mLatitude, mLongitude;

    public ForecastIOWorkerTask(ForecastIO FIO, double latitude, double longitude){
        mFIO = FIO;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    @Override
    protected ForecastIO doInBackground(Object[] params) {
        mFIO.getForecast("" + mLatitude, "" + mLongitude);
        return mFIO;
    }
}
