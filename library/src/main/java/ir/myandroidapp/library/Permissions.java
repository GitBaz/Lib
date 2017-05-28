package ir.myandroidapp.library;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by kam.amir on 5/28/17.
 */

public class Permissions {

    String pack;
    Context context;

    public Permissions(String pck,Context ctx){
        pack = pck;
        context = ctx;
    }

    public void getFineLocation(Activity act){
        PackageManager manager = context.getPackageManager();
        int hasPermission = manager.checkPermission("android.permission.ACCESS_FINE_LOCATION", pack);
        if (hasPermission == manager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public boolean checkFineLocation(){
        PackageManager manager = context.getPackageManager();
        int hasPermission = manager.checkPermission("android.permission.ACCESS_FINE_LOCATION", pack);
        return hasPermission == manager.PERMISSION_GRANTED;
    }

}
