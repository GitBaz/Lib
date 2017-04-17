package ir.myandroidapp.library.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 2/14/17.
 */
public class Searcher extends LinearLayout {

    EditText searcher;

    public Searcher(Context context, Core core) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.searcher, this);
        searcher = (EditText) findViewById(R.id.searcher_edittext);
        searcher.setTypeface(core.setTypeFace());
    }

    public void setOnSearchListener(final TextView.OnEditorActionListener listener) {
        searcher.setOnEditorActionListener(listener);
    }
}