package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 2/18/17.
 */
public class DetailViewEditable extends LinearLayout {

    String name;
    ImageView delete;
    LinearLayout nameLayout;
    TextView nameText;

    public DetailViewEditable(Context context, Core core, WindowManager wm, String text) {
        super(context);
        Size size = new Size(context,wm);

        name=text;
        LayoutInflater.from(context).inflate(R.layout.detail_view_editable, this);

        nameLayout = (LinearLayout) findViewById(R.id.detail_view_name_layout);

        nameText = new TextView(context);
        nameText.setTypeface(core.setTypeFace());
        nameText.setText(text);
        nameText.setTextSize(size.getdp(5));

        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = Gravity.START|Gravity.CENTER;
        params.setMargins(size.getdp(8),size.getdp(8),size.getdp(8),size.getdp(8));
        params.weight=1;
        nameText.setLayoutParams(params);

        nameLayout.addView(nameText);


        delete = new ImageView(context);
        delete.setImageResource(R.drawable.ic_delete_red);
        LayoutParams params1 = new LayoutParams(-2,-2);
        params1.setMargins(0,size.getdp(8),0,size.getdp(8));

        delete.setLayoutParams(params1);

        nameLayout.addView(delete);


    }

    public ImageView delete (){
        return delete;
    }

    public String getName(){
        return name;
    }

    public LinearLayout getLayout() {
        return nameLayout;
    }

}