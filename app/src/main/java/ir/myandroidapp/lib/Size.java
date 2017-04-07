package ir.myandroidapp.lib;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by kam.amir on 4/7/17.
 */

public class Size {

    WindowManager windowManager;
    Context context;

    public Size(Context ctx, WindowManager wm) {
        windowManager = wm;
        context = ctx;
    }

    public int getH() {
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);

        return dm.heightPixels;
    }

    public int getW() {
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;
    }

    public int getdp(int dpValue) {
        float d = context.getResources().getDisplayMetrics().density;
        return Math.round(dpValue * d);
    }

    public LinearLayout fabSpace() {
        LinearLayout space = new LinearLayout(context);
        space.setLayoutParams(new ViewGroup.LayoutParams(0, getdp(80)));
        return space;
    }

}
