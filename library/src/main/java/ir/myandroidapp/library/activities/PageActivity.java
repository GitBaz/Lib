package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    String extra = "";

    String info="";

    DetailView dv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        core = new Core(this);
        core.forceRTLIfSupported(getWindow());
        action = new ActionBar(this, core, R.layout.page_activity);
        action.setMenu(R.menu.page_about);
        action.setTitle("نخچه");
        action.setBackIcon(this);
        action.setNavIcon(R.drawable.ic_location_light, new Primary().getMapActivity());
        action.setOnItemClick(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MessageView(PageActivity.this,core,info);
                return false;
            }
        });
        setContentView(action);

        logo = (ImageView) findViewById(R.id.page_activity_pic);
        brand = (TextView) findViewById(R.id.page_activity_name);
        container = (LinearLayout) findViewById(R.id.page_activity_container);

        getIntent().getStringExtra("pageId");

        final ProgressView pv = new ProgressView(this);
        pv.show();

        new BackendData(core).getPageById(extra, new BackendData.GetUserPage() {
            @Override
            public void onExists(BackendPage page) {
                pv.cancel();
                info = page.getInfo();

                Picasso.with(core.context).load(page.getLogo()).into(logo);
                brand.setText(page.getBrand());

                dv = new DetailView(core.context,core,getWindowManager(),page.getDetail());

                ProgressBar pb = new ProgressBar(core.context);
                container.addView(pb);
                new BackendData(core).getUserPagePosts(extra, new BackendData.GetUserPagePosts() {
                    @Override
                    public void onExists(BackendObject[] objects) {
                        container.removeAllViews();
                        for(int i=0;i<objects.length;i++)
                            container.addView(new CardView(core.context,core,objects[i],PageActivity.this));
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