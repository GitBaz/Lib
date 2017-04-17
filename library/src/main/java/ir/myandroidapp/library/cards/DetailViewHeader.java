package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 2/18/17.
 */
public class DetailViewHeader extends LinearLayout{

    ImageView cart,fav;

    public DetailViewHeader(Context context, Core core, String title, String subtitle, String primary,
                            String secondary, OnClickListener onFav, OnClickListener onCart){
        super(context);

        LayoutInflater.from(context).inflate(R.layout.detail_view_header,this);

        TextView titleView = (TextView) findViewById(R.id.detail_view_header_title);
        TextView subtitleView = (TextView) findViewById(R.id.detail_view_header_subtitle);
        TextView primaryPrice = (TextView) findViewById(R.id.detail_view_header_primary_price);
        TextView secondaryPrice = (TextView) findViewById(R.id.detail_view_header_secondary_price);

        cart = (ImageView) findViewById(R.id.detail_view_header_add_cart);
        fav = (ImageView) findViewById(R.id.detail_view_header_add_fav);

        titleView.setTypeface(core.setTypeFace());
        subtitleView.setTypeface(core.setTypeFace());
        primaryPrice.setTypeface(core.setTypeFace());
        secondaryPrice.setTypeface(core.setTypeFace());

        core.drawRedLine(secondaryPrice);

        titleView.setText(title);
        subtitleView.setText(subtitle);
        primaryPrice.setText(primary);
        secondaryPrice.setText(secondary);

        primaryPrice.setText(core.priceString(primary)+" تومان");
        secondaryPrice.setText(core.priceString(secondary)+" تومان");

        fav.setOnClickListener(onFav);
        cart.setOnClickListener(onCart);

    }

}