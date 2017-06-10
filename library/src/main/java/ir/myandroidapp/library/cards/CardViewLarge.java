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
import ir.myandroidapp.library.Remember;
import ir.myandroidapp.library.activities.ItemActivity;
import ir.myandroidapp.library.activities.Page;
import ir.myandroidapp.library.activities.PageActivity;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;

/**
 * Created by kam.amir on 2/16/17.
 */
public class CardViewLarge extends LinearLayout{

    Core core;
    ImageView fav;

    public CardViewLarge(final Context context, Core cre, final Activity act, final BackendObject obj){
        super(context);
        core= cre;


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, ItemActivity.class);
                intent.putExtra("id",obj.getId());
                context.startActivity(intent);
            }
        });

        LayoutInflater.from(context).inflate(R.layout.card_view_large,this);

        TextView primary = (TextView) findViewById(R.id.card_view_large_primary_price);
        TextView secondary = (TextView) findViewById(R.id.card_view_large_secondary_price);
        ImageView imageView = (ImageView) findViewById(R.id.card_view_large_iv);
        TextView titleView = (TextView) findViewById(R.id.card_view_large_title);
        TextView subTitleView = (TextView) findViewById(R.id.card_view_large_sub_title);
        fav = (ImageView) findViewById(R.id.card_view_large_add_fav);

        TextView water = (TextView) findViewById(R.id.card_view_large_water);
        water.setTypeface(core.setTypeFace());

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

        fav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                core.fav(obj.getId());
            }
        });

    }

    public CardViewLarge(final Context context, Core cre, final Activity act, final BackendPage obj){
        super(context);
        core= cre;


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, PageActivity.class);
                intent.putExtra("pageId",obj.getId());
                context.startActivity(intent);
            }
        });

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

        titleView.setText(obj.getBrand());
        subTitleView.setText(core.stringLimit(obj.getInfo(),15));

        primary.setVisibility(GONE);
        secondary.setVisibility(GONE);

        core.drawRedLine(secondary);

        Picasso.with(context).load(obj.getLogo()).into(imageView);

        fav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                core.fav(obj.getId());
            }
        });

    }

}