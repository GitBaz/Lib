package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 2/18/17.
 */
public class DetailView extends LinearLayout {

    public DetailView(Context context, Core core, WindowManager wm, String details){
        super(context);

        Size size = new Size(context,wm);

        LayoutInflater.from(context).inflate(R.layout.detail_view,this);


        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.detail_view, this);

        LinearLayout nameLayout = (LinearLayout) layout.findViewById(R.id.detail_view_name_layout);
        LinearLayout valueLayout = (LinearLayout) layout.findViewById(R.id.detail_view_value_layout);

        String[] dtls = core.divide(details,'|');


        for (int i = 0; i < dtls.length; i++) {
            TextView name = new TextView(context);
            TextView value = new TextView(context);
            name.setTypeface(core.setTypeFace());
            value.setTypeface(core.setTypeFace());
            name.setText(core.divide(dtls[i],':')[0]);
            value.setText(core.divide(dtls[i],':')[1]);
            name.setTextSize(size.getdp(5));
            value.setTextSize(size.getdp(5));

            LinearLayout line = new LinearLayout(context);
            line.setLayoutParams(new ViewGroup.LayoutParams(-1, 1));
            line.setBackgroundColor(core.getColor(R.color.colorDivider));
            LinearLayout line2 = new LinearLayout(context);
            line2.setLayoutParams(new ViewGroup.LayoutParams(-1, 1));
            line2.setBackgroundColor(core.getColor(R.color.colorDivider));

            LayoutParams params = new LayoutParams(-2, -2);
            params.topMargin = size.getdp(4);
            params.bottomMargin = size.getdp(4);
            params.leftMargin = size.getdp(8);
            params.rightMargin = size.getdp(8);
            name.setLayoutParams(params);
            value.setLayoutParams(params);

            nameLayout.addView(name);
            nameLayout.addView(line);
            valueLayout.addView(value);
            valueLayout.addView(line2);


        }
    }

}