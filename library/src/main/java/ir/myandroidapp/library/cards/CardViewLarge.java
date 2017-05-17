package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendObject;

/**
 * Created by kam.amir on 2/16/17.
 */
public class CardViewLarge extends LinearLayout{

    Core core;
    ImageView fav;

    public CardViewLarge(Context context, Core cre, BackendObject obj, OnClickListener onFav){
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

        titleView.setText(obj.getName());
        subTitleView.setText(core.stringLimit(obj.getInfo(),15));

        primary.setText(core.priceString(obj.getPrimaryPrice())+" تومان");
        secondary.setText(core.priceString(obj.getSecondaryPrice())+" تومان");
        core.drawRedLine(secondary);

        Picasso.with(context).load(core.divide(obj.getPics(),'|')[0]).into(imageView);

        fav.setOnClickListener(onFav);

    }

}