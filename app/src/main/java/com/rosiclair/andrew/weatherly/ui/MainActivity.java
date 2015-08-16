package com.rosiclair.andrew.weatherly.ui;

import java.util.ArrayList;
import java.util.Locale;

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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.rosiclair.andrew.weatherly.R;
import com.rosiclair.andrew.weatherly.control.PlayServicesEventHandler;
import com.rosiclair.andrew.weatherly.control.WeatherlyEventHandler;
import com.rosiclair.andrew.weatherly.data.WeatherlyCity;
import com.rosiclair.andrew.weatherly.data.WeatherlyDataModel;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

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


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a CityFragment (defined as a static inner class below).
            return CityFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 1 page for now
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CityFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CityFragment newInstance(int sectionNumber) {
            CityFragment fragment = new CityFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
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

            View rootView = inflater.inflate(R.layout.fragment_this_week_day, container, false);
            return rootView;
        }

    }

    public void update(){

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
