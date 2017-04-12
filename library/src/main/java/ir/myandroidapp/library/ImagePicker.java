package ir.myandroidapp.library;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by kam.amir on 4/10/17.
 */

public class ImagePicker {

    Activity activity;

    public ImagePicker(Activity act) {
        activity = act;
    }

    public void pick() {
        Crop.pickImage(activity);
    }

    public void result(int requestCode, int resultCode, Intent data, GetPath path) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == activity.RESULT_OK) {
            startCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            path.path(Crop.getOutput(data).getPath());
        }
    }

    private void startCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), generateId()));
        Crop.of(source, destination).asSquare().start(activity);
    }

    public interface GetPath {
        void path(String path);
    }

    private final class SIG {
        private SecureRandom random = new SecureRandom();
    }

    public String generateId(){
        return new BigInteger(130,new SIG().random).toString();
    }

}
