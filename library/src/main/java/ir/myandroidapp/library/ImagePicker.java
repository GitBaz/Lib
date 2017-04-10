package ir.myandroidapp.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by kam.amir on 4/10/17.
 */

public class ImagePicker {

    Activity activity;

    public ImagePicker(Activity act) {
        activity = act;
    }

    public void pick(Activity activity) {
        Crop.pickImage(activity);
    }

    public void result(int requestCode, int resultCode, Intent data, Runnable cropped) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == activity.RESULT_OK) {
            startCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            cropped.run();
        }
    }

    private void startCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(activity);
    }


}
