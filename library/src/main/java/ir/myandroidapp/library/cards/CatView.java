package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 3/1/17.
 */
public class CatView extends LinearLayout {

    ImageView iv;
    TextView tv;
    LinearLayout main;

    public CatView(Context context, Core core, String title, int icon){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.cat_view,this);
        iv = (ImageView) findViewById(R.id.cat_view_iv);
        tv = (TextView) findViewById(R.id.cat_view_title);
        tv.setTypeface(core.setTypeFace());
        main = (LinearLayout) findViewById(R.id.cat_view_click);

        iv.setImageResource(icon);
        tv.setText(title);

    }

    public TextView title(){
        return tv;
    }

    public ImageView icon(){
        return iv;
    }

    public String getTitle(){
        return tv.getText().toString();
    }

}
