package com.rosiclair.andrew.weatherly.control;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rosiclair.andrew.weatherly.ui.MainActivity;

/**
 * An event handler for the GoogleApiClient.
 */
public class PlayServicesEventHandler implements GoogleApiClient.ConnectionCallbacks,
                                                GoogleApiClient.OnConnectionFailedListener,
                                                PermissionsManager.PermissionsRequestListener{

    private MainActivity mActivity;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;

    private Location mLastLocation;
    private String mLatitude, mLongitude;

    private WeatherlyEventHandler mWeatherlyEventHandler;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    public boolean mResolvingError = false;
    public static final String STATE_RESOLVING_ERROR = "resolving_error";


    public PlayServicesEventHandler(MainActivity activity, WeatherlyEventHandler eventHandler){
        mActivity = activity;
        mWeatherlyEventHandler = eventHandler;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.

        //Get the newly built API client from the Main Activity
        mGoogleApiClient = mActivity.getGoogleApiClient();

        //Request last known location
        updateLocation();
    }

    public void updateLocation() {
        // If we don't have location request it and try again with the result
        if (!PermissionsManager.checkPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionsManager.askPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION, PermissionsManager.LOCATION_REQUEST_CODE, this);
            return;
        }

        if (mFusedLocationClient == null)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                mLastLocation = location;
                                mWeatherlyEventHandler.onLocationUpdate(mLastLocation);
                            }
                        }
                    });
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.

        if(mResolvingError){
            // Already attempting to resolve an error.
            return;
        }else if(result.hasResolution()){
            try {
                mResolvingError = true;
                result.startResolutionForResult(mActivity, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        }
    }

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment(this);
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(mActivity.getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    @Override
    public void onRequestResult(boolean granted) {
        if (granted)
            updateLocation();
        else {
            //TODO: use dialog manager to prompt reasoning
            return;
        }
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {

        PlayServicesEventHandler mPlayServicesEventHandler;

        public ErrorDialogFragment(){}

        @SuppressLint("ValidFragment")
        public ErrorDialogFragment(PlayServicesEventHandler playServicesEventHandler) {
            mPlayServicesEventHandler = playServicesEventHandler;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GooglePlayServicesUtil.getErrorDialog(errorCode,
                    this.getActivity(), REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            mPlayServicesEventHandler.onDialogDismissed();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == Activity.RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }
}
