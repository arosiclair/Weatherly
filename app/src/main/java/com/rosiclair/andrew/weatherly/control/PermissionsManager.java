package com.rosiclair.andrew.weatherly.control;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.LinkedList;
import java.util.Queue;

public class PermissionsManager {

    public static final int LOCATION_REQUEST_CODE = 0;
    private static Queue<PermissionsRequestListener> locationRequestListeners = new LinkedList<>();

    public static boolean checkPermission(Activity activity, String permission) {
        int result = ContextCompat.checkSelfPermission(activity, permission.toString());
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void askPermission(Activity activity, String permission, int requestCode,
                                     PermissionsRequestListener listener) {
        switch (permission) {
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                locationRequestListeners.add(listener);
                break;
        }

        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                requestCode);
    }

    private static void addListener(Queue<PermissionsRequestListener> queue, PermissionsRequestListener listener) {
        if (queue == null)
            queue = new LinkedList<>();
        queue.add(listener);
    }

    public static void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            switch (permissions[i]) {
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                case Manifest.permission.ACCESS_FINE_LOCATION:
                    notifyListeners(locationRequestListeners, grantResults[i] == PackageManager.PERMISSION_GRANTED);
                    break;
            }
        }
    }

    private static void notifyListeners(Queue<PermissionsRequestListener> listeners, boolean granted) {
        while (!listeners.isEmpty())
            listeners.remove().onRequestResult(granted);
    }

    public interface PermissionsRequestListener {
        void onRequestResult(boolean granted);
    }
}
