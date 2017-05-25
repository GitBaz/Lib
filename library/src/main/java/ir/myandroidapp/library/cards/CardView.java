package ir.myandroidapp.library.cards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.activities.ItemActivity;
import ir.myandroidapp.library.backend.BackendObject;

/**
 * Created by kam.amir on 1/11/17.
 */
public class CardView extends LinearLayout {

    Core core;
    android.support.v7.widget.CardView cardView;

    public CardView(final Context context, Core cre, final BackendObject object, final Activity act){
        super(context);
        core= cre;

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, ItemActivity.class);
                intent.putExtra("id",object.getId());
                context.startActivity(intent);
            }
        });

        LayoutInflater.from(context).inflate(R.layout.card_view,this);
        cardView = (android.support.v7.widget.CardView) findViewById(R.id.card_view_card);

        TextView primary = (TextView) findViewById(R.id.card_view_primary_price);
        TextView secondary = (TextView) findViewById(R.id.card_view_secondary_price);
        ImageView imageView = (ImageView) findViewById(R.id.card_view_imageView);

        primary.setTypeface(core.setTypeFace());
        secondary.setTypeface(core.setTypeFace());

        primary.setText(core.priceString(object.getPrimaryPrice())+" تومان");
        secondary.setText(core.priceString(object.getSecondaryPrice())+" تومان");

        if(object.getSecondaryPrice().equals(""))
            secondary.setVisibility(GONE);

        core.drawRedLine(secondary);

        Picasso.with(context).load(core.divide(object.getPics(),'|')[0]).into(imageView);
    }
}