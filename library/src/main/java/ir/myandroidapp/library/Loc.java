package ir.myandroidapp.library;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by kam.amir on 5/28/17.
 */

public class Loc {

    Core core;
    Activity activity;

    public Loc(Core cre, Activity act) {
        core = cre;
        activity = act;
    }

    public void getPermission() {
        PackageManager manager = core.context.getPackageManager();
        int hasPermission = manager.checkPermission("android.permission.ACCESS_FINE_LOCATION", "com.nokhche.app");
        if (hasPermission == manager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public boolean isPermission(){
        PackageManager manager = core.context.getPackageManager();
        int hasPermission = manager.checkPermission("android.permission.ACCESS_FINE_LOCATION", "com.nokhche.app");
        return hasPermission == manager.PERMISSION_GRANTED ;
    }

    public void getLatLong(final GetStatus status) {
        LocationManager locationManager = (LocationManager) core.context.getSystemService(LOCATION_SERVICE);
        String provider = LocationManager.NETWORK_PROVIDER;
        PackageManager manager = core.context.getPackageManager();
        int hasPermission = manager.checkPermission("android.permission.ACCESS_FINE_LOCATION", "com.nokhche.app");
        if (hasPermission == manager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(provider, 5000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    status.status(location.getLatitude(), location.getLongitude());
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                    core.toast("اینترنت یا مکان نما خاموش است .");
                }
            });
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public interface GetStatus {
        void status(double lat, double lng);
    }

}
