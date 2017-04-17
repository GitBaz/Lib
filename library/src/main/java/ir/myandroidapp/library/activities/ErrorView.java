package ir.myandroidapp.library.activities;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 3/10/17.
 */
public class ErrorView extends LinearLayout{

    public ErrorView(Context context, WindowManager wm, Core core, OnClickListener listener){
        super(context);
        Size size = new Size(context,wm);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutParams params = new LayoutParams(size.getdp(64),size.getdp(64));

        params.setMargins(size.getdp(16),0,size.getdp(16),size.getdp(8));

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.ic_wifi_off_dark);

        TextView textView = new TextView(context);
        textView.setTextSize(20);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(core.getColor(new Primary().getPrimary()));
        textView.setTypeface(core.setTypeFace());
        textView.setText(core.getString(R.string.touch_to_retry));

        addView(imageView);
        addView(textView);

        setOnClickListener(listener);

    }

}
