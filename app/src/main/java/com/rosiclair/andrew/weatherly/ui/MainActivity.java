package com.rosiclair.andrew.weatherly.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.commonsware.cwac.pager.PageDescriptor;
import com.commonsware.cwac.pager.SimplePageDescriptor;
import com.commonsware.cwac.pager.v4.ArrayPagerAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.rosiclair.andrew.weatherly.R;
import com.rosiclair.andrew.weatherly.control.PlayServicesEventHandler;
import com.rosiclair.andrew.weatherly.control.WeatherlyEventHandler;
import com.rosiclair.andrew.weatherly.data.WeatherlyCity;
import com.rosiclair.andrew.weatherly.data.WeatherlyDataModel;
import com.rosiclair.andrew.weatherly.data.WeatherlyDayForecast;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    WeatherlyPagerAdapter mWeatherlyPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private GoogleApiClient mGoogleApiClient;
    private WeatherlyDataModel mDataModel;
    private WeatherlyEventHandler mEventHandler;
    private PlayServicesEventHandler mPlayServicesEventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init the ArrayPagerAdapter with an empty ArrayList of PageDescriptors
        ArrayList<PageDescriptor> pageDescriptors = new ArrayList<>();
        mWeatherlyPagerAdapter = new WeatherlyPagerAdapter(getSupportFragmentManager(), pageDescriptors);

        // Get the ViewPager from the layout and attach the ArrayPagerAdapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mWeatherlyPagerAdapter);

        //Add the Current Location page
        SimplePageDescriptor currentLocation = new SimplePageDescriptor("CURRENT_LOCATION_FRAGMENT", "CURRENT_LOCATION");
        mWeatherlyPagerAdapter.add(currentLocation);
        mViewPager.setCurrentItem(0);

        mDataModel = new WeatherlyDataModel();
        mEventHandler = new WeatherlyEventHandler(this, mDataModel);

        mPlayServicesEventHandler = new PlayServicesEventHandler(this, mEventHandler);
        //Build the GoogleAPIClient and initialize its event handler
        buildGoogleApiClient();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class WeatherlyPagerAdapter extends ArrayPagerAdapter<CityFragment> {

        public WeatherlyPagerAdapter(FragmentManager fragmentManager, ArrayList<PageDescriptor> descriptors) {
            super(fragmentManager, descriptors);
        }

        @Override
        protected CityFragment createFragment(PageDescriptor desc) {
            return(CityFragment.newInstance());
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CityFragment extends Fragment {

        // Returns a new instance of CityFragment
        public static CityFragment newInstance() {
            return new CityFragment();
        }

        public CityFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_city, container, false);

            //Set Lobster typeface for the City Name
            setCityNameFont(rootView);
            return rootView;
        }

        private void setCityNameFont(View fragmentView) {

            TextView cityName = (TextView) fragmentView.findViewById(R.id.city_name);
            Typeface lobster = Typeface.createFromAsset(getActivity().getAssets(), "lobster.otf");
            cityName.setTypeface(lobster);
        }
    }

    public static class ThisWeekDayFragment extends Fragment {

        public ThisWeekDayFragment(){

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_this_week_day, container, false);
        }

    }

    //Update views with new data
    public void update(){
        WeatherlyCity currentLocation = mDataModel.getCurrentLocation();
        WeatherlyDayForecast todaysForecast = currentLocation.getTodaysForecast();

        Fragment fragment = mWeatherlyPagerAdapter.getExistingFragment(0);
        View rootView = fragment.getView();

        TextView currentTemp = (TextView) rootView.findViewById(R.id.current_temp);
        currentTemp.setText(String.valueOf(currentLocation.getCurrentTemp()));
        TextView currentConditions = (TextView) rootView.findViewById(R.id.current_conditions);
        currentConditions.setText(String.valueOf(currentLocation.getCurrentCondition()));
        TextView feelsLike = (TextView) rootView.findViewById(R.id.feels_like_temp);
        feelsLike.setText(String.valueOf(currentLocation.getFeelsLike()) + "°");
        TextView wind = (TextView) rootView.findViewById(R.id.wind_speed);
        wind.setText(String.valueOf(currentLocation.getWind()) + " mph");
        TextView humidity = (TextView) rootView.findViewById(R.id.humidity);
        humidity.setText(String.valueOf(currentLocation.getHumidity()) + "%");
        TextView visibility = (TextView) rootView.findViewById(R.id.visibility);
        visibility.setText(String.valueOf(currentLocation.getVisibility()) + " mi.");

        /*for(int i = 1; i < mWeatherlyPagerAdapter.getCount(); i++){

        }*/


    }

    /**
     * Changes the ActionBar layout to the custom_action_bar layout with the custom logo.
     */
    private void initActionBarTitle(){
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.custom_action_bar, null);

        this.getSupportActionBar().setCustomView(v);
    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(mPlayServicesEventHandler)
                .addOnConnectionFailedListener(mPlayServicesEventHandler)
                .addApi(LocationServices.API)
                .build();
    }

    //Pass this callback to the event handler
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        mPlayServicesEventHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Preserve the error resolution status
        outState.putBoolean(PlayServicesEventHandler.STATE_RESOLVING_ERROR, mPlayServicesEventHandler.mResolvingError);
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public WeatherlyDataModel getDataModel() {
        return mDataModel;
    }

    public WeatherlyEventHandler getEventHandler() {
        return mEventHandler;
    }

    public PlayServicesEventHandler getPlayServicesEventHandler() {
        return mPlayServicesEventHandler;
    }


}
