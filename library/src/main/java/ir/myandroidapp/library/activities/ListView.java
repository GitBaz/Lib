package ir.myandroidapp.library.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

public class ListView extends LinearLayout {

    Core core;
    Context ctx;
    String address="";
    GetAddress adrs;

    public ListView(Context context, Core cre,GetAddress ga,int cat) {
        super(context);
        setOrientation(VERTICAL);
        core = cre;
        ctx = context;
        adrs=ga;
        try {

            try {

                addJson(cat);

            }catch (JSONException e){

            }

        }catch (IOException e){

        }
    }

    public void addJson(int cat) throws JSONException, IOException {
        String json = null;
        InputStream is = ctx.getAssets().open("cat.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        String work = new JSONObject(json).getJSONObject(new JSONObject(json).names().get(cat).toString()).toString();
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
                    String nj = "";
                    try {
                        nj = new JSONObject(json).get(names[j]).toString();
                        removeAllViews();
                        address += names[j] + ",";
                        create(nj);

                    } catch (JSONException e) {
                        address += names[j];
                        adrs.address(address);
                    }
                }
            });
            addView(layout);
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
            }
        });
        addView(layout);

    }

    public interface GetAddress {
        void address(String s);
    }

}