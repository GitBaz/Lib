package ir.myandroidapp.library.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.MessageView;
import ir.myandroidapp.library.Dialogs.ProgressView;
import ir.myandroidapp.library.Loc;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.cards.CardView;
import ir.myandroidapp.library.cards.DetailView;

/**
 * Created by kam.amir on 5/26/17.
 */

public class PageActivity extends Activity {

    Core core;
    ActionBar action;

    ImageView logo;
    TextView brand;
    LinearLayout container;
    TextView number;

    String extra = "";

    String info = "";

    DetailView dv;

    LinearLayout detailContainer;

    android.support.v7.widget.CardView call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        core = new Core(this);
        core.forceRTLIfSupported(getWindow());
        action = new ActionBar(this, core, R.layout.page_activity);
        action.setMenu(R.menu.page_about);
        action.setTitle("نخچه");
        action.setBackIcon(this);
        action.setOnItemClick(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MessageView(PageActivity.this, core, info);
                return false;
            }
        });
        setContentView(action);

        number = (TextView) findViewById(R.id.page_activity_number);
        logo = (ImageView) findViewById(R.id.page_activity_pic);
        brand = (TextView) findViewById(R.id.page_activity_name);
        container = (LinearLayout) findViewById(R.id.page_activity_container);
        call = (android.support.v7.widget.CardView) findViewById(R.id.page_activity_call_card);
        detailContainer = (LinearLayout) findViewById(R.id.page_activity_detail_container);

        extra = getIntent().getStringExtra("pageId");

        final ProgressView pv = new ProgressView(this);
        pv.show();

        new BackendData(core).getPageById(extra, new BackendData.GetUserPage() {
            @Override
            public void onExists(final BackendPage page) {
                pv.cancel();
                info = page.getInfo();

                if (!page.getLat().equals("")) {
                    action.setNavIcon(R.drawable.ic_location_light, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (new Loc(core, PageActivity.this).isPermission()) {
                                Class target = new Primary().getMapact();
                                Intent intent = new Intent(PageActivity.this, target);
                                intent.putExtra("lat", page.getLat());
                                intent.putExtra("lng", page.getLng());
                                startActivity(intent);
                            } else
                                new Loc(core, PageActivity.this).getPermission();
                        }
                    });

                }

                Picasso.with(core.context).load(page.getLogo()).into(logo);
                brand.setText(page.getBrand());

                dv = new DetailView(core.context, core, getWindowManager(), page.getDetail());

                number.setText(page.getNumber());

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone_no = number.getText().toString();
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone_no));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (getPackageManager().checkPermission("android.permission.CALL_PHONE", "com.nokhche.app") ==
                                getPackageManager().PERMISSION_GRANTED)
                            startActivity(callIntent);
                        else
                            ActivityCompat.requestPermissions(PageActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                    }
                });

                detailContainer.addView(new DetailView(core.context, core, getWindowManager(), page.getDetail()));

                ProgressBar pb = new ProgressBar(core.context);
                container.addView(pb);
                new BackendData(core).getUserPagePosts(extra, new BackendData.GetUserPagePosts() {
                    @Override
                    public void onExists(BackendObject[] objects) {
                        container.removeAllViews();
                        for (int i = 0; i < objects.length; i++)
                            container.addView(new CardView(core.context, core, objects[i], PageActivity.this));
                    }

                    @Override
                    public void onNotExists() {
                        container.removeAllViews();
                    }

                    @Override
                    public void onFailure() {
                        container.removeAllViews();
                    }
                });
            }

            @Override
            public void onNotExists() {
                finish();
                core.toast("دسترسی به این صفحه وجود ندارد.");
                pv.cancel();
            }

            @Override
            public void onFailure() {
                finish();
                pv.cancel();
            }
        });

    }
}