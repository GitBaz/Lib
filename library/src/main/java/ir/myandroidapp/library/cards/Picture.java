package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 2/14/17.
 */
public class Picture extends LinearLayout {

    ImageView image;

    public Picture(Context context, WindowManager wm, String pic, int w, int h) {
        super(context);
        Size size = new Size(context, wm);

        LayoutInflater.from(context).inflate(R.layout.picture, this);

        image = (ImageView) findViewById(R.id.tp_a);

        image.getLayoutParams().height = size.getW() / h;
        image.getLayoutParams().width = size.getW() / w;

        Picasso.with(context).load(pic).into(image);
    }
}