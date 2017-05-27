package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendComment;
import ir.myandroidapp.library.backend.SimpleResponse;
import ir.myandroidapp.library.cards.CardComment;

/**
 * Created by kam.amir on 5/27/17.
 */

public class CommentList extends Activity{

    Core core;
    ActionBar actionBar;

    LinearLayout layout;

    ScrollView sv;
    LinearLayout sl;

    String extra;

    BackendComment bc;

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
        actionBar = new ActionBar(this,core,sl);
        actionBar.setBackIcon(this);

        setContentView(actionBar);
        core.forceRTLIfSupported(getWindow());
        actionBar.setTitle("نظرات");
        sv.addView(layout);

        extra = getIntent().getStringExtra("commentId");

        bc = new BackendComment(core);

        show();

        actionBar.setNavIcon(R.drawable.ic_add_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DialogInput(core.context, core, "نظر شما", "نظر خود را بنویسید .", 150, new DialogInput.Response() {
                    @Override
                    public void resp(String result) {
                        bc.create(result,extra);
                        bc.writeComment(new SimpleResponse() {
                            @Override
                            public void onSuccess() {
                                show();
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                });


            }
        });


    }

    void show(){
        layout.removeAllViews();
        ProgressBar pb = new ProgressBar(core.context);
        layout.addView(pb);
        bc.readComment(extra, new BackendComment.CommentResponse() {
            @Override
            public void onSuccess(BackendComment[] cmt) {
                layout.removeAllViews();
                for(int i =0;i<cmt.length;i++) {
                    CardComment comment = new CardComment(core.context, core, cmt[i]);
                    layout.addView(comment);
                }
            }
        });
    }
}
