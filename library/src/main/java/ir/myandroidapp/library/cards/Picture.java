package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;
import ir.myandroidapp.library.backend.BackendObject;

/**
 * Created by kam.amir on 2/14/17.
 */
public class Picture extends LinearLayout {

    ImageView image,image2;

    public Picture(Context context, Core cre, WindowManager wm, BackendObject obj1, BackendObject obj2) {
        super(context);
        Size size = new Size(context, wm);

        LayoutInflater.from(context).inflate(R.layout.picture, this);

        image = (ImageView) findViewById(R.id.tp_a);
        image2 = (ImageView) findViewById(R.id.tp_b);

        image.getLayoutParams().height = size.getW()/2;
        image2.getLayoutParams().height = size.getW()/2;

        Picasso.with(context).load(cre.divide(obj1.getPics(),'|')[0]).into(image);
        Picasso.with(context).load(cre.divide(obj2.getPics(),'|')[0]).into(image2);

    }

    public ImageView getImage(){
        return image;
    }

    public ImageView getImage2(){
        return image2;
    }

}