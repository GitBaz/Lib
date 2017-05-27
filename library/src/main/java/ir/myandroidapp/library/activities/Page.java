package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DecideView;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.cards.CardView;

/**
 * Created by kam.amir on 5/17/17.
 */

public class Page extends LinearLayout {

    Core core;

    ImageView pic;
    TextView name, add;
    Button edit;
    LinearLayout itemContainer;
    Activity activity;

    public Page(Context context, Core cre, BackendPage page, Activity act) {
        super(context);
        core = cre;
        activity = act;
        LayoutInflater.from(context).inflate(R.layout.page, this);

        pic = (ImageView) findViewById(R.id.page_pic);
        name = (TextView) findViewById(R.id.page_name);
        add = (TextView) findViewById(R.id.page_add_item);
        edit = (Button) findViewById(R.id.page_edit_page);
        itemContainer = (LinearLayout) findViewById(R.id.page_item_container);

        name.setTypeface(core.setTypeFace());
        add.setTypeface(core.setTypeFace());
        edit.setTypeface(core.setTypeFace());

        Picasso.with(context).load(page.getLogo()).into(pic);
        name.setText(page.getBrand());

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,AddItem.class);
                activity.startActivity(intent);
            }
        });

        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                new DecideView(core.context, core, "آیا مایل به حذف کسب و کار خود هستید ؟", new Runnable() {
                    @Override
                    public void run() {
                        new BackendData(core).deletePage(new Runnable() {
                            @Override
                            public void run() {
                                activity.finish();
                            }
                        });
                    }
                });
            }
        });

    }

    public Page addObjects(BackendObject[] objects){
        int itemCount = objects.length;
        CardView[] cards = new CardView[itemCount];

        for (int i = 0; i < itemCount; i++) {
            cards[i] = new CardView(core.context,core,objects[i],activity);
            itemContainer.addView(cards[i]);
        }
        return this;
    }

}