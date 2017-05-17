package ir.myandroidapp.library.cards;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DecideView;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 2/18/17.
 */
public class DetailViewEditable extends LinearLayout {

    ImageView delete;
    LinearLayout nameLayout;
    TextView nameText;

    String nameIp = "";
    String valueIp = "";
    String total = "";
    WindowManager windowManager;
    Core core;
    LinearLayout layout;

    public DetailViewEditable(Context context, Core cre, WindowManager wm, LinearLayout detailLayout) {
        super(context);
        Size size = new Size(context, wm);
        core = cre;
        windowManager = wm;
        layout = detailLayout;

        LayoutInflater.from(context).inflate(R.layout.detail_view_editable, this);

        nameLayout = (LinearLayout) findViewById(R.id.detail_view_name_layout);

        nameText = new TextView(context);
        nameText.setTypeface(core.setTypeFace());
        nameText.setTextSize(size.getdp(5));

        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = Gravity.START | Gravity.CENTER;
        params.setMargins(size.getdp(8), size.getdp(8), size.getdp(8), size.getdp(8));
        params.weight = 1;
        nameText.setLayoutParams(params);

        nameLayout.addView(nameText);


        delete = new ImageView(context);
        delete.setImageResource(R.drawable.ic_delete_red);
        LayoutParams params1 = new LayoutParams(-2, -2);
        params1.setMargins(0, size.getdp(8), 0, size.getdp(8));

        delete.setLayoutParams(params1);

        nameLayout.addView(delete);

    }

    public ImageView delete() {
        return delete;
    }

    public String getTotal() {
        return total;
    }

    public LinearLayout getLayout() {
        return nameLayout;
    }

    public void add() {
        new DialogInput(core.context, core, "عنوان", "برای مثال وزن", 15, new DialogInput.Response() {
            @Override
            public void resp(String result) {
                nameIp = result;

                new DialogInput(core.context, core, "مقدار", "برای مثال ۴ کیلوگرم", 15, new DialogInput.Response() {
                    @Override
                    public void resp(String result) {
                        valueIp = result;
                        total = total + nameIp + ":" + valueIp + ":|";
                        setView();
                    }
                }).getEditText().setFilters(core.charAndNumOnly());
            }
        }).getEditText().setFilters(core.charAndNumOnly());

    }

    private void setView(){
        layout.removeAllViews();
        if (total.length() > 0) {
            final String[] divided = core.divide(total, '|');

            int count = divided.length;

            for (int i = 0; i < count; i++) {

                final String name = core.divide(divided[i], ':')[0];
                final String value = core.divide(divided[i], ':')[1];
                layout.addView(setActions(name, value, divided[i]));
            }
        }
    }

    private DetailViewEditable setActions(final String name ,final String value,final String divided){

        DetailViewEditable dve = new DetailViewEditable(core.context,core,windowManager,layout);

        dve.nameText.setText(name+" : "+value);

        dve.delete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DecideView(core.context, core, core.getString(R.string.remove_sure), new Runnable() {
                    @Override
                    public void run() {
                        total = total.replace(divided + "|", "");
                        setView();
                    }
                });
            }
        });

        dve.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogInput(core.context, core, name, value, 15,
                        new DialogInput.Response() {
                            @Override
                            public void resp(String result) {
                                total = total.replace(value, result);
                            }
                        }).getEditText().setText(value);
            }
        });
        return dve;
    }

}