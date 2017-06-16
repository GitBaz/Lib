package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.backtory.java.internal.BacktoryObject;

import java.io.IOException;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.CatDialog;
import ir.myandroidapp.library.Dialogs.LocationDialog;
import ir.myandroidapp.library.ImagePicker;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Ui.Waiter;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.backend.ObjectUploader;
import ir.myandroidapp.library.cards.DetailViewEditable;

/**
 * Created by kam.amir on 3/12/17.
 */
public class AddItem extends Activity {

    Core core;

    TextView tvName;
    EditText etName;

    TextView tvPp;
    EditText etPp;

    TextView tvSp;
    EditText etSp;

    TextView tvInfo;
    EditText etInfo;

    TextView tvNumber;
    EditText etNumber;

    LinearLayout addPageContainer;
    ToggleButton addPage, catButton, locButton;

    TextView iv_title;
    ImageView[] iv = new ImageView[5];

    LinearLayout detailsLayout;
    DetailViewEditable dve;

    ActionBar action;

    ImagePicker picker;

    BackendData data;

    String address = "";
    String location = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        core = new Core(this);
        core.forceRTLIfSupported(getWindow());

        action = new ActionBar(this, core, R.layout.add_item);
        action.setTitle("محصول جدید");
        action.setTickIcon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkers())
                    new ObjectUploader(core, "Products", picker.getPaths(), AddItem.this).create(getObject()).upload();
            }
        });
        action.turnOnFloatingButton(true);
        action.getFloatingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dve.add();
            }
        });
        action.getFloatingButton().setImageResource(R.drawable.ic_add_white);

        setContentView(action);

        detailsLayout = (LinearLayout) findViewById(R.id.ai_details);
        dve = new DetailViewEditable(this, core, getWindowManager(), detailsLayout);

        data = new BackendData(core);

        tvName = (TextView) findViewById(R.id.ai_name_tv);
        etName = (EditText) findViewById(R.id.ai_name_et);
        tvName.setTypeface(core.setTypeFace());
        etName.setTypeface(core.setTypeFace());

        tvPp = (TextView) findViewById(R.id.ai_pp_tv);
        etPp = (EditText) findViewById(R.id.ai_pp_et);
        tvPp.setTypeface(core.setTypeFace());
        etPp.setTypeface(core.setTypeFace());

        tvSp = (TextView) findViewById(R.id.ai_sp_tv);
        etSp = (EditText) findViewById(R.id.ai_sp_et);
        tvSp.setTypeface(core.setTypeFace());
        etSp.setTypeface(core.setTypeFace());

        tvInfo = (TextView) findViewById(R.id.ai_info_tv);
        etInfo = (EditText) findViewById(R.id.ai_info_et);
        tvInfo.setTypeface(core.setTypeFace());
        etInfo.setTypeface(core.setTypeFace());

        etNumber = (EditText) findViewById(R.id.ai_number_et);
        tvNumber = (TextView) findViewById(R.id.ai_number_tv);

        etNumber.setTypeface(core.setTypeFace());
        tvNumber.setTypeface(core.setTypeFace());

        iv_title = (TextView) findViewById(R.id.ai_pics_title);
        iv_title.setTypeface(core.setTypeFace());

        addPage = (ToggleButton) findViewById(R.id.ai_add_page);
        addPage.setTypeface(core.setTypeFace());

        catButton = (ToggleButton) findViewById(R.id.ai_cat_btn);
        catButton.setTypeface(core.setTypeFace());

        catButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CatDialog cat = new CatDialog(core.context, core, new CatDialog.GetAddress() {
                    @Override
                    public void address(String s) {
                        address = s;
                        core.toast(s);
                    }
                });
                if (b)
                    cat.show();
            }
        });

        locButton = (ToggleButton) findViewById(R.id.ai_loc_btn);
        locButton.setTypeface(core.setTypeFace());

        locButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LocationDialog ld = new LocationDialog(core.context, core, new LocationDialog.GetAddress() {
                    @Override
                    public void address(String s) {
                        location = s;
                    }
                });
                if (b)
                    ld.show();

            }
        });

        addPageContainer = (LinearLayout) findViewById(R.id.ai_add_page_container);

        iv[0] = (ImageView) findViewById(R.id.ai_iv1);
        iv[1] = (ImageView) findViewById(R.id.ai_iv2);
        iv[2] = (ImageView) findViewById(R.id.ai_iv3);
        iv[3] = (ImageView) findViewById(R.id.ai_iv4);
        iv[4] = (ImageView) findViewById(R.id.ai_iv5);

        picker = new ImagePicker(this, core, iv, getContentResolver(), 100);

        addPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addPage.isChecked()) {

                } else {

                    addPageContainer.removeView(addPage);
                    final Waiter waiter = new Waiter(core, addPageContainer);

                    data.getUserPage(new BackendData.GetUserPage() {
                        @Override
                        public void onExists(BackendPage page) {
                            waiter.cancel();
                            addPageContainer.addView(addPage);
                            core.toast("به کسب کار '" + page.getBrand() + "' متصل شد");
                        }

                        @Override
                        public void onNotExists() {
                            core.toast("کسب و کاری یافت نشد !");
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            picker.result(requestCode, resultCode, data);
        } catch (IOException e) {

        }
    }

    public BackendObject getObject() {
        BackendObject obj = new BackendObject();
        obj.setPics(core.combineNoNull(picker.getLinks(), '|'));
        obj.setInfo(etInfo.getText().toString());
        obj.setDetails(dve.getTotal());
        obj.setName(etName.getText().toString());
        obj.setPlace("");
        obj.setPrimaryPrice(etPp.getText().toString());
        obj.setSecondaryPrice(etSp.getText().toString());
        obj.setCat(address);
        obj.setNumber(etNumber.getText().toString());
        obj.setLocation(location);
        if (addPage.isChecked())
            obj.setPage("1");
        else
            obj.setPage("0");

        return obj;

    }

    boolean checkers() {
        if (catButton.isChecked() && !address.equals("") || addPage.isChecked()) {
            if (!etNumber.getText().toString().equals(""))
                return true;
            else {
                core.toast("شماره تماس وارد نشده است.");
                return false;
            }
        } else {
            core.toast("دسته بندی انتخاب نشده");
            return false;
        }
    }


}