package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.internal.BacktoryUser;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendComment;
import ir.myandroidapp.library.backend.BackendData;
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


        show();

        actionBar.setNavIcon(R.drawable.ic_add_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DialogInput(core.context, core, "نظر شما", "نظر خود را بنویسید .", 150, new DialogInput.Response() {
                    @Override
                    public void resp(String result) {
                        BacktoryObject object = new BacktoryObject("Comments");
                        object.put("user", BacktoryUser.getCurrentUser().getUserId());
                        object.put("comment", result);
                        object.put("itemId", extra);
                        object.put("permission", "0");
                        object.put("username", BacktoryUser.getCurrentUser().getFirstName());
                        object.saveInBackground(new BacktoryCallBack<Void>() {
                            @Override
                            public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                                if (backtoryResponse.isSuccessful()){

                                }
                                else {
                                    core.toast(core.getString(R.string.connection_error));
                                }
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
        new BackendData(core).readComment(extra, new BackendData.CommentResponse() {
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