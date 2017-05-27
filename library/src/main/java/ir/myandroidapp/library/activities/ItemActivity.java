package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Remember;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.cards.DetailView;
import ir.myandroidapp.library.cards.DetailViewHeader;
import ir.myandroidapp.library.cards.ViewPager;

/**
 * Created by kam.amir on 5/25/17.
 */

public class ItemActivity extends Activity {

    Core core;
    ActionBar action;

    BackendObject object;
    LinearLayout container;

    String id="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        core = new Core(this);
        id=getIntent().getStringExtra("id");

        new BackendData(core).get(id, new BackendData.Getobj() {
            @Override
            public void onSuccess(BackendObject obj) {
                object=obj;
                set();

            }

            @Override
            public void onFailure() {

            }
        });

    }

    public void set(){
        core.forceRTLIfSupported(getWindow());
        action = new ActionBar(this, core, R.layout.item_view);
        action.setTitle(object.getName());
        action.setBackIcon(this);
        setContentView(action);

        container = (LinearLayout) findViewById(R.id.item_view_container);

        final ViewPager vp = new ViewPager(this, 1,core, getWindowManager(),
                core.divide(object.getPics(), '|').length, new ViewPager.Set() {
            @Override
            public Object setImages(ViewGroup container, int position) {
                final ImageView imageView = new ImageView(core.context);
                imageView.setBackgroundColor(core.getColor(ir.myandroidapp.library.R.color.colorCard));
                Picasso.with(core.context).load(core.divide(object.getPics(),'|')[position]).into(imageView);
                container.addView(imageView);
                return imageView;
            }
        });

        container.addView(vp);
        container.addView(new DetailViewHeader(this, core, object, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                core.fav(object.getId());
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemActivity.this,CommentList.class);
                intent.putExtra("commentId",object.getId());
                startActivity(intent);
            }
        }));

        if(!object.getDetails().equals(""))
            container.addView(new DetailView(this,core,getWindowManager(),object.getDetails()));




    }
}