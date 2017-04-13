package ir.myandroidapp.library.activities;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 3/10/17.
 */
public class BrandView extends LinearLayout {

    Size size;

    public BrandView(Context context, Core core, WindowManager windowManager){
        super(context);
        size = new Size(context,windowManager);
        setOrientation(VERTICAL);
        setBackgroundColor(core.getColor(new Primary().getPrimary()));
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(size.getdp(16),0,size.getdp(16),size.getdp(64));

        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setTextColor(core.getColor(new Primary().getPrimaryLight()));
        textView.setTypeface(core.setTypeFace());
        textView.setTextSize(64);
        textView.setText(new Primary().getBrand());

        ProgressBar pb = new ProgressBar(context);
        pb.setLayoutParams(params);

        addView(textView);
        addView(pb);

    }

}
