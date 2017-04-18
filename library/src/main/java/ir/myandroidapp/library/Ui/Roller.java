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
    TextView title,more;

    public Roller(Context context, Core cre, String ttl, View.OnClickListener moreListener){
        super(context);
        core= cre;

        LayoutInflater.from(context).inflate(R.layout.roller,this);

        title = (TextView) findViewById(R.id.roller_title);
        more = (TextView) findViewById(R.id.roller_more);
        container = (LinearLayout) findViewById(R.id.roller_container);

        title.setTypeface(core.setTypeFace());
        more.setTypeface(core.setTypeFace());

        title.setText(ttl);
        more.setText("بیشتر");

        more.setOnClickListener(moreListener);

    }

    public LinearLayout getContainer(){
        return container;
    }

}