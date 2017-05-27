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

public class SearchPostsActivity extends Activity {

    LinearLayout layout;
    ScrollView sv;
    LinearLayout sl;
    int pic = R.drawable.ic_list_white;

    Core core;
    ActionBar actionBar;

    String extraCat = "";
    String extraCity = "";
    String extraName = "";

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

        extraCat = getIntent().getStringExtra("cat");
        extraCity = getIntent().getStringExtra("city");
        extraName = getIntent().getStringExtra("name");


        getPosts();


    }

    void getPosts() {
        layout.removeAllViews();

        new BackendData(core).getSearch(extraName, extraCat, extraCity, new BackendData.GetObject() {
            @Override
            public void onSuccess(BackendObject[] obj) {
                int count = obj.length;
                if(count>0)
                for (int i = 0; i < count; i++) {
                    layout.addView(new CardViewLarge(core.context, core, SearchPostsActivity.this, obj[i]));

                }
            }

            @Override
            public void onFailure() {
                finish();
                core.toast("یافت نشد");
            }
        });

    }

    void getPages() {
        layout.removeAllViews();

        new BackendData(core).getSearchPages(extraName, extraCat, extraCity, new BackendData.GetPag() {
            @Override
            public void onSuccess(BackendPage[] obj) {
                int count = obj.length;
                if(count>0)
                    for (int i = 0; i < count; i++) {
                        layout.addView(new CardViewLarge(core.context, core, SearchPostsActivity.this, obj[i]));

                    }
            }

            @Override
            public void onFailure() {
                finish();
                core.toast("یافت نشد");
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
