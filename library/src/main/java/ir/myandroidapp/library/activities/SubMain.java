package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.CatDialog;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Ui.Roller;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.cards.CardView;
import ir.myandroidapp.library.cards.CatView;
import ir.myandroidapp.library.cards.Picture;
import ir.myandroidapp.library.cards.ViewPager;

/**
 * Created by kam.amir on 5/26/17.
 */

public class SubMain extends Activity {

    Core core;
    ActionBar action;
    LinearLayout layout, pagerLayout, roller1Layout, roller2Layout, roller3Layout, pictureLayout;

    ViewPager pager;
    Roller roller, roller2, roller3;

    String extra = "";

    String a = "کسب و کار ها";
    String b = "نخچه";
    String c = "بازاریابی شبکه ای";
    String d = "استخدام و کاریابی";

    String keya = "bz";
    String keyb = "no";
    String keyc = "net";
    String keyd = "mark";

    String main = "";

    Picture picture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout scrollContainer = new LinearLayout(this);
        ScrollView sv = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        core = new Core(this);
        action = new ActionBar(this, core, scrollContainer);
        action.setTitle("نخچه");
        action.setBackIcon(this);
        action.setNavIcon(R.drawable.ic_list_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CatDialog(core.context, core, new CatDialog.GetAddress() {
                    @Override
                    public void address(String s) {
                        core.intentActivityPutExtra(PostsActivity.class,"cat",s);
                    }
                }).show();
            }
        });
        layout.setBackgroundColor(core.getColor(R.color.colorBackground));

        scrollContainer.addView(sv);
        sv.addView(layout);
        pagerLayout = new LinearLayout(this);
        roller1Layout = new LinearLayout(this);
        roller2Layout = new LinearLayout(this);
        roller3Layout = new LinearLayout(this);
        pictureLayout = new LinearLayout(this);
        pictureLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        layout.addView(pagerLayout);
        layout.addView(roller2Layout);
        layout.addView(roller1Layout);
        layout.addView(pictureLayout);
        layout.addView(roller3Layout);
        setContentView(action);

        core.forceRTLIfSupported(getWindow());

        extra = getIntent().getStringExtra("subMain");
        unlock();

        roller = new Roller(this, core, "آخرین پست ها");
        roller2 = new Roller(this, core, "پست های ویژه");
        roller3 = new Roller(this, core, "آخرین کسب و کارها");

        picture = new Picture(this, core, getWindowManager());

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
                                Intent intent = new Intent(SubMain.this, PageActivity.class);
                                intent.putExtra("pageId", page[i].getId());
                                startActivity(intent);
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

        new BackendData(core).getObjectByPlace("SPECIAL" + extra, new BackendData.GetObjects() {
            @Override
            public void onExists(BackendObject[] obj) {
                int len = obj.length;
                if(len>0) {
                    for (int i = 0; i < len; i++)
                        roller2.getContainer().addView(new CardView(core.context, core, obj[i], SubMain.this));
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

        new BackendData(core).getObjectByCat(main, new BackendData.GetObjects() {
            @Override
            public void onExists(BackendObject[] obj) {
                int len = obj.length;
                if(len>0) {
                    for (int i = 0; i < len; i++) {
                        roller.getContainer().addView(new CardView(core.context, core, obj[i], SubMain.this));
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

        new BackendData(core).getPageByPlace("SPONSER" + extra, new BackendData.GetPages() {
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

        new BackendData(core).getPageByCat(main, new BackendData.GetPages() {
            @Override
            public void onExists(BackendPage[] page) {
                int len = page.length;
                if(len>0) {
                    for (int i = 0; i < len; i++) {
                        roller3.getContainer().addView(new CardView(core.context, core, page[i], SubMain.this));
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

        roller1Layout.setVisibility(View.GONE);
        roller2Layout.setVisibility(View.GONE);
        roller3Layout.setVisibility(View.GONE);
        pictureLayout.setVisibility(View.GONE);
        pagerLayout.setVisibility(View.GONE);

    }

    private void unlock() {
        if (extra.equals(keya))
            main = a;
        else if (extra.equals(keyb))
            main = b;
        else if (extra.equals(keyc))
            main = c;
        else if (extra.equals(keyd))
            main = d;
    }

}