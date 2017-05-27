package ir.myandroidapp.library.activities;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.CatDialog;
import ir.myandroidapp.library.Dialogs.LocationDialog;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 2/14/17.
 */
public class Searcher extends LinearLayout {

    EditText searcher;

    ToggleButton cat,loc;
    String strCat="",strLoc="";

    public Searcher(Context context, final Core core) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.searcher, this);
        searcher = (EditText) findViewById(R.id.searcher_edittext);
        searcher.setTypeface(core.setTypeFace());

        cat = (ToggleButton) findViewById(R.id.ap_add_cat);
        loc = (ToggleButton) findViewById(R.id.ap_add_loc);

        cat.setTypeface(core.setTypeFace());
        loc.setTypeface(core.setTypeFace());

        cat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    CatDialog cd = new CatDialog(core.context, core, new CatDialog.GetAddress() {
                        @Override
                        public void address(String s) {
                            strCat = s;
                        }
                    });
                    cd.show();
                }
            }
        });

        loc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    LocationDialog ld = new LocationDialog(core.context, core, new LocationDialog.GetAddress() {
                        @Override
                        public void address(String s) {
                            strLoc = s;
                        }
                    });
                    ld.show();
                }
            }
        });


        searcher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {



                return true;
            }
        });
    }

    public void setOnSearchListener(final TextView.OnEditorActionListener listener,GetStatus status) {
        searcher.setOnEditorActionListener(listener);
        status.status(strCat,strLoc);
    }

    public interface GetStatus{
        void status(String cat,String loc);
    }
}