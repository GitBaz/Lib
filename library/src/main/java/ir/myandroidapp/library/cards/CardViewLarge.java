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
 * Created by kam.amir on 2/16/17.
 */
public class CardViewLarge extends LinearLayout{

    Core core;
    ImageView fav;

    public CardViewLarge(Context context, Core cre, String link, String title, String subTitle,
                         String primaryPrice, String secondaryPrice, OnClickListener onFav){
        super(context);
        core= cre;

        LayoutInflater.from(context).inflate(R.layout.card_view_large,this);

        TextView primary = (TextView) findViewById(R.id.card_view_large_primary_price);
        TextView secondary = (TextView) findViewById(R.id.card_view_large_secondary_price);
        ImageView imageView = (ImageView) findViewById(R.id.card_view_large_iv);
        TextView titleView = (TextView) findViewById(R.id.card_view_large_title);
        TextView subTitleView = (TextView) findViewById(R.id.card_view_large_sub_title);
        fav = (ImageView) findViewById(R.id.card_view_large_add_fav);

        primary.setTypeface(core.setTypeFace());
        secondary.setTypeface(core.setTypeFace());
        titleView.setTypeface(core.setTypeFace());
        subTitleView.setTypeface(core.setTypeFace());

        titleView.setText(title);
        subTitleView.setText(subTitle);

        primary.setText(core.priceString(primaryPrice)+" تومان");
        secondary.setText(core.priceString(secondaryPrice)+" تومان");
        core.drawRedLine(secondary);

        Picasso.with(context).load(link).into(imageView);

        fav.setOnClickListener(onFav);

    }

}
