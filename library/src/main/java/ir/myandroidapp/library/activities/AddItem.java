package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.backtory.java.internal.BacktoryUser;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.ImagePicker;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Ui.Waiter;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.backend.BackendUser;
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

    LinearLayout addPageContainer;
    ToggleButton addPage;

    TextView iv_title;
    ImageView[] iv = new ImageView[5];

    LinearLayout detailsLayout;
    DetailViewEditable dve;

    FloatingActionButton floatingButton;
    Toolbar toolbar;
    ActionBar action;

    ImagePicker picker;

    BackendData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_action);

        core = new Core(this);
        core.forceRTLIfSupported(getWindow());

        detailsLayout = (LinearLayout) findViewById(R.id.ai_details);
        dve = new DetailViewEditable(this, core, getWindowManager(), detailsLayout);

        data = new BackendData(core);

        floatingButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dve.add();
            }
        });

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

        iv_title = (TextView) findViewById(R.id.ai_pics_title);
        iv_title.setTypeface(core.setTypeFace());

        addPage = (ToggleButton) findViewById(R.id.ai_add_page);
        addPage.setTypeface(core.setTypeFace());

        addPageContainer = (LinearLayout) findViewById(R.id.ai_add_page_container);

        iv[0] = (ImageView) findViewById(R.id.ai_iv1);
        iv[1] = (ImageView) findViewById(R.id.ai_iv2);
        iv[2] = (ImageView) findViewById(R.id.ai_iv3);
        iv[3] = (ImageView) findViewById(R.id.ai_iv4);
        iv[4] = (ImageView) findViewById(R.id.ai_iv5);

        picker = new ImagePicker(this, core, iv, getContentResolver(), 100);

        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        action = new ActionBar(core);
        action.actionBarInit(toolbar);
        action.actionBarTitle("محصول جدید");
        action.actionbarTickIcon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ObjectUploader(core, "Products", picker.getPaths()).create(getObject()).upload();
            }
        });

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
                picker.result(requestCode, resultCode, data);
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
                obj.setCat("");
                if (addPage.isChecked())
                    obj.setPage("1");
                else
                    obj.setPage("0");

                return obj;
            }




    }