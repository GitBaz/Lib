package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Ui.Roller;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.cards.CardView;
import ir.myandroidapp.library.cards.Picture;
import ir.myandroidapp.library.cards.ViewPager;

/**
 * Created by kam.amir on 5/27/17.
 */

public class main extends LinearLayout {


    ScrollView sv;
    LinearLayout layout, pagerLayout, roller1Layout, roller2Layout, roller3Layout, pictureLayout;

    ViewPager pager;
    Roller roller, roller2, roller3;
    Picture picture;

    Core cre;

    public main(final Context context, final Core core, final WindowManager wm, final Activity act) {
        super(context);
        cre= core;

        sv = new ScrollView(context);
        layout = new LinearLayout(context);
        sv.addView(layout);

        addView(sv);

        layout.setOrientation(VERTICAL);

        pagerLayout = new LinearLayout(context);
        roller1Layout = new LinearLayout(context);
        roller2Layout = new LinearLayout(context);
        roller3Layout = new LinearLayout(context);
        pictureLayout = new LinearLayout(context);

        roller = new Roller(context, core, "آخرین پست ها");
        roller2 = new Roller(context, core, "پست های ویژه");
        roller3 = new Roller(context, core, "آخرین کسب و کارها");
        picture = new Picture(context, core, wm);

        roller1Layout.setVisibility(View.GONE);
        roller2Layout.setVisibility(View.GONE);
        roller3Layout.setVisibility(View.GONE);
        pictureLayout.setVisibility(View.GONE);
        pagerLayout.setVisibility(View.GONE);

        new BackendData(core).getPageByPlace("SLIDER", new BackendData.GetPages() {
            @Override
            public void onExists(final BackendPage[] page) {
                pager = new ViewPager(core.context, 2, core, wm, page.length, new ViewPager.Set() {

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
                                Intent intent = new Intent(context, PageActivity.class);
                                intent.putExtra("pageId", page[i].getId());
                                act.startActivity(intent);
                            }
                        });
                        return imageView;

                    }
                });
                pagerLayout.addView(pager);
                if(page.length>0)
                    pagerLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNotExists() {
            }

            @Override
            public void onFailure() {
            }
        });

        new BackendData(core).getObjectByPlace("SPECIAL" , new BackendData.GetObjects() {
            @Override
            public void onExists(BackendObject[] obj) {
                int len = obj.length;
                if(len>0) {
                    for (int i = 0; i < len; i++)
                        roller2.getContainer().addView(new CardView(core.context, core, obj[i], act));
                    roller2Layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNotExists() {
            }

            @Override
            public void onFailure() {

            }
        });

        new BackendData(core).getObjectByCat("", new BackendData.GetObjects() {
            @Override
            public void onExists(BackendObject[] obj) {
                int len = obj.length;
                if(len>0) {
                    for (int i = 0; i < len; i++) {
                        roller.getContainer().addView(new CardView(core.context, core, obj[i], act));
                        if(i>14)
                            break;
                    }
                    roller1Layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNotExists() {
            }

            @Override
            public void onFailure() {

            }
        });

        new BackendData(core).getPageByPlace("SPONSER", new BackendData.GetPages() {
            @Override
            public void onExists(BackendPage[] obj) {
                if(obj.length==2) {
                    picture.setObjects(obj[0], obj[1]);
                    pictureLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNotExists() {

            }

            @Override
            public void onFailure() {

            }
        });

        new BackendData(core).getPageByCat("", new BackendData.GetPages() {
            @Override
            public void onExists(BackendPage[] page) {
                int len = page.length;
                if(len>0) {
                    for (int i = 0; i < len; i++) {
                        roller3.getContainer().addView(new CardView(core.context, core, page[i], act));
                        if(i>14)
                            break;
                    }
                    roller3Layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNotExists() {

            }

            @Override
            public void onFailure() {

            }
        });


        roller1Layout.addView(roller);
        roller2Layout.addView(roller2);
        roller3Layout.addView(roller3);
        pictureLayout.addView(picture);


        layout.addView(pagerLayout);
        layout.addView(roller2Layout);
        layout.addView(roller1Layout);
        layout.addView(pictureLayout);
        layout.addView(roller3Layout);

    }

}
