package ir.myandroidapp.library.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Remember;
import ir.myandroidapp.library.Size;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.cards.CardViewLarge;
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

    FrameLayout fl;

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
        action.setNavIcon(R.drawable.ic_shop_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(object.getPage().equals("0")){
                    core.toast("کسب و کاری وجود ندارد.");
                }else{
                    new BackendData(core).getPageByUser(object.getUser(), new BackendData.GetUserPage() {
                        @Override
                        public void onExists(BackendPage page) {
                            core.intentActivityPutExtra(PageActivity.class,"pageId",page.getId());
                        }

                        @Override
                        public void onNotExists() {
                            core.toast("کسب و کاری وجود ندارد.");
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }
            }
        });
        setContentView(action);

        container = (LinearLayout) findViewById(R.id.item_view_container);

        final ViewPager vp = new ViewPager(this, 1,core, getWindowManager(),
                core.divide(object.getPics(), '|').length, new ViewPager.Set() {
            @Override
            public Object setImages(ViewGroup container, int position) {
                fl = new FrameLayout(core.context);
                fl.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                final ImageView imageView = new ImageView(core.context);
                imageView.setBackgroundColor(core.getColor(ir.myandroidapp.library.R.color.colorCard));
                Picasso.with(core.context).load(core.divide(object.getPics(),'|')[position]).into(imageView);
                fl.addView(imageView);
                container.addView(fl);

                TextView water = new TextView(core.context);
                water.setText("نخچه");
                water.setTextColor(core.getColor(R.color.white));
                water.setTypeface(core.setTypeFace());
                water.setBackgroundColor(core.getColor(R.color.colorDark));
                int mg = new Size(core.context,getWindowManager()).getdp(8);
                int pd = new Size(core.context,getWindowManager()).getdp(8);
                water.setPadding(pd,pd,pd,pd);

                LinearLayout textC = new LinearLayout(core.context);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                textC.setLayoutParams(params);

                textC.addView(water);
                textC.setGravity(Gravity.BOTTOM | Gravity.RIGHT);

                fl.addView(textC);

                return fl;
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

        DetailView dv = new DetailView(this,core,getWindowManager(),"شماره تماس"+":"+object.getNumber()+":|");

        container.addView(dv);

        dv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_no = object.getNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone_no));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (getPackageManager().checkPermission("android.permission.CALL_PHONE", "com.nokhche.app") ==
                        getPackageManager().PERMISSION_GRANTED)
                    startActivity(callIntent);
                else
                    ActivityCompat.requestPermissions(ItemActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

            }
        });



        new BackendData(core).getPageByUser(object.getUser(), new BackendData.GetUserPage() {
            @Override
            public void onExists(BackendPage page) {
                container.addView(new CardViewLarge(core.context,core,ItemActivity.this,page));
            }

            @Override
            public void onNotExists() {

            }

            @Override
            public void onFailure() {

            }
        });


    }
}