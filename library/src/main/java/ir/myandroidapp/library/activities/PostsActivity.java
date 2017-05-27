package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.cards.CardViewLarge;

/**
 * Created by kam.amir on 5/27/17.
 */

public class PostsActivity extends Activity {

    LinearLayout layout;
    ScrollView sv;
    LinearLayout sl;
    int pic = R.drawable.ic_list_white;

    Core core;
    ActionBar actionBar;

    String extra = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sl = new LinearLayout(this);
        sv = new ScrollView(this);
        sl.addView(sv);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        sl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        core = new Core(this);
        actionBar = new ActionBar(this, core, sl);
        actionBar.setBackIcon(this);
        ac();
        setContentView(actionBar);
        core.forceRTLIfSupported(getWindow());
        sv.addView(layout);
        actionBar.setTitle("محصولات");

        extra = getIntent().getStringExtra("cat");

        getPosts();


    }

    void getPosts() {
        layout.removeAllViews();
        new BackendData(core).getObjectByCat(extra, new BackendData.GetObjects() {
            @Override
            public void onExists(BackendObject[] obj) {
                int count = obj.length;
                for (int i = 0; i < count; i++) {
                    layout.addView(new CardViewLarge(core.context, core, PostsActivity.this, obj[i]));

                }
            }

            @Override
            public void onNotExists() {

            }

            @Override
            public void onFailure() {

            }
        });

    }

    void getPages() {
        layout.removeAllViews();
        new BackendData(core).getPageByCat(extra, new BackendData.GetPages() {
            @Override
            public void onExists(BackendPage[] page) {
                int count = page.length;
                for (int i = 0; i < count; i++) {
                    layout.addView(new CardViewLarge(core.context, core, PostsActivity.this, page[i]));

                }
            }

            @Override
            public void onNotExists() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    void ac() {
        if (pic == R.drawable.ic_shop_white) {
            pic = R.drawable.ic_list_white;
        } else {
            pic = R.drawable.ic_shop_white;
        }
        actionBar.setNavIcon(pic, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pic == R.drawable.ic_shop_white)
                    getPages();
                else
                    getPosts();
                ac();
            }
        });

    }
}
