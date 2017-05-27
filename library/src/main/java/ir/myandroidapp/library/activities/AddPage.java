package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.CatDialog;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.Dialogs.LocationDialog;
import ir.myandroidapp.library.ImagePicker;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.backend.BackendPageUploader;
import ir.myandroidapp.library.cards.DetailViewEditable;

/**
 * Created by kam.amir on 5/2/17.
 */

public class AddPage extends Activity {

    ImageView logo;
    TextView brand;
    EditText info;
    EditText phone;

    TextView purchaseWarning;

    ActionBar action;
    ImagePicker picker;
    Core core;

    Button addDetail;

    LinearLayout detailContainer;
    DetailViewEditable dve;

    ToggleButton cat, loc;

    String strCat;
    String strLoc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        core = new Core(this);
        core.forceRTLIfSupported(getWindow());

        action = new ActionBar(this, core, R.layout.add_page);
        action.setTitle("کسب و کار جدید");
        action.setTickIcon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkers())
                    new BackendPageUploader(core, "Pages", picker.getPaths()[0], AddPage.this).create(getPage()).upload();
            }
        });

        setContentView(action);

        logo = (ImageView) findViewById(R.id.add_page_logo);
        brand = (TextView) findViewById(R.id.add_page_brand);
        info = (EditText) findViewById(R.id.add_page_info);
        phone = (EditText) findViewById(R.id.add_page_phone);
        addDetail = (Button) findViewById(R.id.add_page_add_detail);

        brand.setTypeface(core.setTypeFace());
        info.setTypeface(core.setTypeFace());
        phone.setTypeface(core.setTypeFace());
        addDetail.setTypeface(core.setTypeFace());

        cat = (ToggleButton) findViewById(R.id.ap_add_cat);
        loc = (ToggleButton) findViewById(R.id.ap_add_loc);

        cat.setTypeface(core.setTypeFace());
        loc.setTypeface(core.setTypeFace());

        cat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
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
                if (b) {
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

        picker = new ImagePicker(this, core, logo, getContentResolver(), 1500);

        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogInput(AddPage.this, core, "نام کسب و کار", "برای مثال :‌ فروشگاه نخچه", 35,
                        new DialogInput.Response() {
                            @Override
                            public void resp(String result) {
                                brand.setText(result);
                            }
                        });
            }
        });

        detailContainer = (LinearLayout) findViewById(R.id.add_page_detail_container);

        dve = new DetailViewEditable(core.context, core, getWindowManager(), detailContainer);

        purchaseWarning = (TextView) findViewById(R.id.add_page_purchase_warning);
        purchaseWarning.setTypeface(core.setTypeFace());

        addDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dve.add();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        picker.result(requestCode, resultCode, data);
    }

    public BackendPage getPage() {
        BackendPage pg = new BackendPage();
        pg.setBrand(brand.getText().toString());
        pg.setLogo(picker.getLinks()[0]);
        pg.setInfo(info.getText().toString());
        pg.setNumber(phone.getText().toString());
        pg.setDetail(dve.getTotal());
        pg.setCat(strCat);
        pg.setLocation(strLoc);
        return pg;
    }

    boolean checkers() {
        if (strLoc!="" && strCat!="" && cat.isChecked() && loc.isChecked())
            return true;
        else {
            core.toast("دسته بندی یا مکان انتخاب نشده است");
            return false;
        }
    }

}