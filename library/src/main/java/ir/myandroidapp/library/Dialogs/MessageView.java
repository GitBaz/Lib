package ir.myandroidapp.library.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 5/26/17.
 */

public class MessageView extends Dialog {

    Core core;

    public MessageView(Context context, Core cre, String textMessage) {
        super(context);
        core = cre;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.decide_view);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView message = (TextView) findViewById(R.id.decide_message);
        Button yes = (Button) findViewById(R.id.decide_yes);
        Button no = (Button) findViewById(R.id.decide_no);

        message.setTypeface(core.setTypeFace());
        yes.setTypeface(core.setTypeFace());
        yes.setText("باشه");
        no.setVisibility(View.GONE);

        message.setText(textMessage);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        show();
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }


}
