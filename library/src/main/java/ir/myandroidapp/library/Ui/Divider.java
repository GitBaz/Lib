package ir.myandroidapp.library.Ui;

import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 4/18/17.
 */

public class Divider extends LinearLayout {

    public Divider (Context context, WindowManager wm){
        super(context);
        Size size = new Size(context,wm);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                size.getdp(1));
        params.setMargins(size.getdp(16),size.getdp(16),size.getdp(16),size.getdp(16));
        setLayoutParams(params);
        setBackgroundColor(context.getResources().getColor(R.color.colorDivider));
    }

}