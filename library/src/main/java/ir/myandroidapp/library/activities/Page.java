package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.backtory.java.internal.BacktoryObject;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendObject;

/**
 * Created by kam.amir on 5/17/17.
 */

public class Page extends Activity {

    Core core;
    ActionBar bar;

    Toolbar toolbar;

    LinearLayout layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_action);

        core = new Core(this);
        bar = new ActionBar(core);

        bar.actionBarInit(toolbar);
        bar.actionBarTitle(this.getIntent().getStringExtra("page_name").toString());
        bar.actionBarNavIcon(R.drawable.ic_info_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    BacktoryObject.GetQuery("")
            }
        });

        layout = (LinearLayout) findViewById(R.id.content_page);

    }



}