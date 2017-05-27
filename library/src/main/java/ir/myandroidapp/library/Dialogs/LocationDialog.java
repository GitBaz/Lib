package ir.myandroidapp.library.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 5/26/17.
 */

public class LocationDialog extends Dialog {


    Core core;
    Context ctx;
    LinearLayout content;
    String address = "";
    GetAddress adrs;

    public LocationDialog(Context context, Core cre, GetAddress address) {
        super(context);
        core = cre;
        ctx = context;
        adrs = address;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cat_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        content = (LinearLayout) findViewById(R.id.cat_dialog_content);

        try {
            try {
                addJson();

            } catch (JSONException e) {

            }
        } catch (IOException a) {

        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public void addJson() throws JSONException, IOException {
        String json = null;
        InputStream is = ctx.getAssets().open("location.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        String work = json;


        create(work);

    }

    public String[] parseObject(String json) throws JSONException {
        JSONObject mainObject = new JSONObject(json);
        String[] result = new String[mainObject.names().length()];
        for (int i = 0; i < mainObject.names().length(); i++) {
            result[i] = mainObject.names().get(i).toString();
        }
        return result;
    }

    public String[] parseArray(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        String[] result = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            result[i] = array.get(i).toString();
        }
        return result;
    }

    public void create(final String json) throws JSONException {

        final String[] names;
        if (json.startsWith("{"))
            names = parseObject(json);
        else
            names = parseArray(json);

        for (int i = 0; i < names.length; i++) {
            final int j = i;
            LinearLayout layout = (LinearLayout) LayoutInflater.from(ctx).inflate(R.layout.cat_line, new LinearLayout(ctx));
            TextView text = (TextView) layout.findViewById(R.id.cat_line_text);
            text.setTypeface(core.setTypeFace());
            text.setText(names[i]);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nj="";
                    try {
                        nj = new JSONObject(json).get(names[j]).toString();
                        content.removeAllViews();
                        address += names[j] + ",";
                        create(nj);

                    } catch (JSONException e) {
                        cancel();
                        address += names[j]+",";
                        adrs.address(address);
                    }
                }
            });
            content.addView(layout);
        }
        LinearLayout layout = (LinearLayout) LayoutInflater.from(ctx).inflate(R.layout.cat_line, new LinearLayout(ctx));
        TextView text = (TextView) layout.findViewById(R.id.cat_line_text);
        text.setTypeface(core.setTypeFace());
        text.setText("اینجا");
        text.setTextColor(core.getColor(R.color.colorPrimary));
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adrs.address(address);
                cancel();
            }
        });
        content.addView(layout);

    }

    public interface GetAddress {
        void address(String s);
    }


}