package ir.myandroidapp.library;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import ir.myandroidapp.library.Dialogs.DecideView;
import ir.myandroidapp.library.Ui.Rounder;

import static ir.myandroidapp.library.R.id.imageView;

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

    int radius = 0;

    public ImagePicker(Activity act, Core cre, ImageView[] images, ContentResolver cr,int rds) {
        activity = act;
        imageViews = images;
        resolver = cr;

        radius = rds;

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

    public ImagePicker(Activity act, Core cre, ImageView image, ContentResolver cr,int rds) {
        activity = act;
        imageViews = new ImageView[1];
        imageViews[0] = image;
        resolver = cr;

        radius = rds;

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

    public void result(int requestCode, int resultCode, Intent data) throws IOException {
        if (requestCode == Crop.REQUEST_PICK && resultCode == activity.RESULT_OK) {
            startCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {


            String path = Crop.getOutput(data).getPath();

//            Bitmap water = core.watermark(
//            MediaStore.Images.Media.getBitmap(resolver,data.getData())
//            ,"نخچه",core.getColor(R.color.white));
//

          //  MediaStore.Images.Media.getBitmap(resolver,data.getData());

         //   String pathed = core.storeImage(water).getPath();


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





//        Bitmap bmp = imageViews[number].getDrawingCache();
//        imageViews[number].setImageBitmap(bmp);
       // imageViews[number].setImageBitmap(core.watermark(bmp,"نخچه",core.getColor(R.color.white)));

//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
//           // Bitmap water = core.watermark(bitmap,"نخچه",core.getColor(R.color.white));
//            imageViews[number].setImageBitmap(bitmap);
//        } catch (IOException e) {
//        }

    }

    public String[] getPaths() {
        return paths;
    }

    public String[] getLinks() {
        return links;
    }


}