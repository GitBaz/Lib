package ir.myandroidapp.library.Ui;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import ir.myandroidapp.library.Core;

/**
 * Created by kam.amir on 5/9/17.
 */

public class Waiter {

    ProgressBar bar;
    LinearLayout wLayout;
    LinearLayout mainLayout;

    public Waiter(Core core, LinearLayout layout){

        mainLayout = layout;
        wLayout = new LinearLayout(core.context);
        bar= new ProgressBar(core.context);

        wLayout.setOrientation(LinearLayout.VERTICAL);
        wLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        wLayout.setGravity(Gravity.CENTER);
        wLayout.addView(bar);

        layout.addView(wLayout);

    }

    public void cancel(){
        mainLayout.removeView(wLayout);
    }

}