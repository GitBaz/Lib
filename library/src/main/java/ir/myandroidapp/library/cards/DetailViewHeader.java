package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendObject;

/**
 * Created by kam.amir on 2/18/17.
 */
public class DetailViewHeader extends LinearLayout{

    ImageView fav;
    Button comment;

    public DetailViewHeader(Context context, Core core, BackendObject object, OnClickListener onFav,OnClickListener cmnt){
        super(context);

        LayoutInflater.from(context).inflate(R.layout.detail_view_header,this);

        TextView titleView = (TextView) findViewById(R.id.detail_view_header_title);
        TextView subtitleView = (TextView) findViewById(R.id.detail_view_header_subtitle);
        TextView primaryPrice = (TextView) findViewById(R.id.detail_view_header_primary_price);
        TextView secondaryPrice = (TextView) findViewById(R.id.detail_view_header_secondary_price);
        comment = (Button) findViewById(R.id.comment_btn);

        comment.setOnClickListener(cmnt);

        fav = (ImageView) findViewById(R.id.detail_view_header_add_fav);

        titleView.setTypeface(core.setTypeFace());
        subtitleView.setTypeface(core.setTypeFace());
        primaryPrice.setTypeface(core.setTypeFace());
        secondaryPrice.setTypeface(core.setTypeFace());
        comment.setTypeface(core.setTypeFace());

        core.drawRedLine(secondaryPrice);

        titleView.setText(object.getName());
        subtitleView.setText(object.getInfo());
        primaryPrice.setText(core.priceString(object.getPrimaryPrice())+" تومان");
        secondaryPrice.setText(core.priceString(object.getSecondaryPrice())+" تومان");

        fav.setOnClickListener(onFav);

    }
}