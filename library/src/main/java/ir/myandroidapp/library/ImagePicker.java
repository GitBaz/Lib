package ir.myandroidapp.library;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import ir.myandroidapp.library.Dialogs.DecideView;
import ir.myandroidapp.library.Ui.Rounder;

/**
 * Created by kam.amir on 4/10/17.
 */

public class ImagePicker {

    Activity activity;
    String link = "";
    ImageView[] imageViews;

    int number = 0;
    int[] ivOn;
    String[] paths;
    String[] links;

    Core core;
    ContentResolver resolver;
    int round = 0;

    public ImagePicker(Activity act, Core cre, ImageView[] images, ContentResolver cr, int radius) {
        activity = act;
        imageViews = images;
        resolver = cr;
        round = radius;

        core = cre;
        int imageCount = images.length;

        ivOn = new int[imageCount];
        paths = new String[imageCount];
        links = new String[imageCount];

        for (int i = 0; i < imageCount; i++) {
            paths[i] = "";
            links[i] = "";
            ivOn[i] = 0;
        }

        for (int i = 0; i < imageCount; i++) {
            onClick(imageViews[i], i);
        }

    }

    public ImagePicker(Activity act, Core cre, ImageView image, ContentResolver cr, int radius) {
        activity = act;
        imageViews = new ImageView[1];
        imageViews[0] = image;
        resolver = cr;
        round = radius;

        core = cre;
        int imageCount = 1;

        ivOn = new int[imageCount];
        paths = new String[imageCount];
        links = new String[imageCount];

        for (int i = 0; i < imageCount; i++) {
            paths[i] = "";
            links[i] = "";
            ivOn[i] = 0;
        }

        for (int i = 0; i < imageCount; i++) {
            onClick(imageViews[i], i);
        }
    }

    private void pick() {
        Crop.pickImage(activity);
    }

    public void result(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == activity.RESULT_OK) {
            startCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {

            String path = Crop.getOutput(data).getPath();

            if (core.fileMoreThan(path, 200))
                core.toast(core.getString(R.string.more_than_200_kb));
            else {
                paths[number] = path;
                links[number] = link;
                setImage(getUri(data));
            }
        }
    }

    public Uri getUri(Intent result) {
        return Crop.getOutput(result);
    }

    private void startCrop(Uri source) {
        String id = generateId();
        link = new Primary().getUploadLink()+id;
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), id));
        Crop.of(source, destination).asSquare().start(activity);
    }

    private final class SIG {
        private SecureRandom random = new SecureRandom();
    }

    private String generateId() {
        return new BigInteger(130, new SIG().random).toString();
    }

    private void onClick(View view, final int count) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = count;
                if (ivOn[count] == 0)
                    pick();
                else
                    new DecideView(core.context, core, core.getString(R.string.replace_pic_sure),
                            new Runnable() {
                                @Override
                                public void run() {
                                    pick();
                                }
                            });
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                number = count;


                if (ivOn[count] == 1)
                    new DecideView(core.context, core, core.getString(R.string.remove_pic_sure),
                            new Runnable() {
                                @Override
                                public void run() {
                                    clearImage();
                                }
                            });

                return true;
            }
        });

    }

    private void clearImage() {
        imageViews[number].setImageResource(R.drawable.ic_add_photo_dark);
        ivOn[number] = 0;
        paths[number] = "";
        links[number] = "";
    }

    private void setImage(Uri uri) {

        imageViews[number].setImageResource(R.drawable.ic_add_photo_dark);
        imageViews[number].setImageURI(uri);
        ivOn[number] = 1;

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
            imageViews[number].setImageBitmap(new Rounder().getRoundedCornerBitmap(bitmap, round));
        } catch (IOException e) {
        }

    }

    public String[] getPaths() {
        return paths;
    }

    public String[] getLinks() {
        return links;
    }


}