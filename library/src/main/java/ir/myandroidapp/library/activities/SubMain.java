package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Ui.Roller;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.cards.Picture;
import ir.myandroidapp.library.cards.ViewPager;

/**
 * Created by kam.amir on 5/26/17.
 */

public class SubMain extends Activity {

    String[] sliders;
    BackendObject[] objSliders;

    String[] lastItems;
    BackendObject[] objLastItems;

    String[] specials;
    BackendObject[] objSpecials;

    String[] lastPages;
    BackendObject[] objLastPages;

    String[] sponsers;
    BackendObject[] objSponsers;


    Core core;
    ActionBar action;
    LinearLayout layout;

    ViewPager pager;
    Roller roller, roller2, roller3;

    Picture pic;

    String extra = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        core = new Core(this);
        action = new ActionBar(this, core, layout);
        action.setTitle("نخچه");
        action.setBackIcon(this);

        setContentView(action);
        core.forceRTLIfSupported(getWindow());

        extra = getIntent().getStringExtra("subMain");

        new BackendData(core).getPageByPlace("SLIDER" + extra, new BackendData.GetPages() {
            @Override
            public void onExists(final BackendPage[] page) {

                pager = new ViewPager(core.context, 2, core, getWindowManager(), page.length, new ViewPager.Set() {
                    @Override
                    public Object setImages(ViewGroup viewGroup, final int i) {

                        final ImageView imageView = new ImageView(core.context);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        imageView.setBackgroundColor(core.getColor(R.color.white));
                        Picasso.with(core.context).load(page[i].getLogo()).into(imageView);
                        viewGroup.addView(imageView);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(SubMain.this,PageActivity.class);
                                intent.putExtra("pageId",page[i].getId());
                                startActivity(intent);
                            }
                        });
                        return imageView;

                    }
                });

                layout.addView(pager);

            }

            @Override
            public void onNotExists() {
core.toast("notExists");
            }

            @Override
            public void onFailure() {
core.toast("faild");
            }
        });




        roller = new Roller(this, core, "آخرین پست ها", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                core.toast("bests");
            }
        });

        roller2 = new Roller(this, core, "پست های ویژه", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                core.toast("bests");
            }
        });

        roller3 = new Roller(this, core, "آخرین کسب و کارها", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                core.toast("bests");
            }
        });

    //    pic = new Picture()


    }
}
