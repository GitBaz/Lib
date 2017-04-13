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
 * Created by kam.amir on 1/11/17.
 */
public class CardView extends LinearLayout {

    Core core;
    android.support.v7.widget.CardView cardView;

    public CardView(Context context, Core cre, String link, String primaryPrice, String secondaryPrice){
        super(context);
        core= cre;

        LayoutInflater.from(context).inflate(R.layout.card_view,this);
        cardView = (android.support.v7.widget.CardView) findViewById(R.id.card_view_card);

        TextView primary = (TextView) findViewById(R.id.card_view_primary_price);
        TextView secondary = (TextView) findViewById(R.id.card_view_secondary_price);
        ImageView imageView = (ImageView) findViewById(R.id.card_view_imageView);

        primary.setTypeface(core.setTypeFace());
        secondary.setTypeface(core.setTypeFace());

        primary.setText(core.priceString(primaryPrice)+" تومان");
        secondary.setText(core.priceString(secondaryPrice)+" تومان");

        if(secondaryPrice.equals(""))
            secondary.setVisibility(GONE);

        core.drawRedLine(secondary);

        Picasso.with(context).load(link).into(imageView);

    }
}