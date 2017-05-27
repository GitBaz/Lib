package ir.myandroidapp.library.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 1/11/17.
 */
public class Roller extends LinearLayout {

    Core core;
    LinearLayout container;
    TextView title;

    public Roller(Context context, Core cre, String ttl){
        super(context);
        core= cre;

        LayoutInflater.from(context).inflate(R.layout.roller,this);

        title = (TextView) findViewById(R.id.roller_title);
        container = (LinearLayout) findViewById(R.id.roller_container);

        title.setTypeface(core.setTypeFace());

        title.setText(ttl);

    }

    public LinearLayout getContainer(){
        return container;
    }

}