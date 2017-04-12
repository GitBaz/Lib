package ir.myandroidapp.library.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 1/5/17.
 */
public class DecideView extends LinearLayout{

    Dialog di;
    Core core;

    public DecideView(Context context, Core cre, String textMessage, final Runnable run){
        super(context);
        core = cre;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        di = new Dialog(context);
        di.requestWindowFeature(Window.FEATURE_NO_TITLE);
        di.setContentView(R.layout.decide_view);

        di.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView message = (TextView) di.findViewById(R.id.decide_message);
        final Button yes = (Button) di.findViewById(R.id.decide_yes);
        Button no = (Button) di.findViewById(R.id.decide_no);

        message.setTypeface(core.setTypeFace());
        yes.setTypeface(core.setTypeFace());
        no.setTypeface(core.setTypeFace());

        message.setText(textMessage);

        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                run.run();
            }
        });

        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = di.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        di.show();
        di.setCancelable(true);
        di.setCanceledOnTouchOutside(true);
    }

    public void cancel(){
        di.cancel();
    }

}