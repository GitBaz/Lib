package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ir.myandroidapp.library.ActionBar;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.ImagePicker;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.backend.BackendPageUploader;

/**
 * Created by kam.amir on 5/2/17.
 */

public class AddPage extends Activity {

    ImageView logo;
    TextView brand;
    EditText info;
    EditText phone;

    EditText telegramLink,webLink;
    TextView purchaseWarning;

    ActionBar action;
    ImagePicker picker;
    Core core;

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page_action);

        core = new Core(this);
        core.forceRTLIfSupported(getWindow());

        logo = (ImageView) findViewById(R.id.add_page_logo);
        brand = (TextView) findViewById(R.id.add_page_brand);
        info = (EditText) findViewById(R.id.add_page_info);

        picker = new ImagePicker(this,core,logo,getContentResolver(),1500);

        brand.setTypeface(core.setTypeFace());
        info.setTypeface(core.setTypeFace());

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

        purchaseWarning = (TextView) findViewById(R.id.add_page_purchase_warning);
        telegramLink = (EditText) findViewById(R.id.add_page_telegram_link);
        webLink = (EditText) findViewById(R.id.add_page_web_link);

        purchaseWarning.setTypeface(core.setTypeFace());
        telegramLink.setTypeface(core.setTypeFace());
        webLink.setTypeface(core.setTypeFace());

        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        action = new ActionBar(core);
        action.actionBarInit(toolbar);
        action.actionBarTitle("کسب و کار جدید");
        action.actionbarTickIcon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackendPageUploader(core, "Pages",picker.getPaths()[0]).create(getPage()).upload();
            }
        });

        phone = (EditText) findViewById(R.id.add_page_phone);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        picker.result(requestCode,resultCode,data);
    }

    public BackendPage getPage(){
        BackendPage pg = new BackendPage();
        pg.setBrand(brand.getText().toString());
        pg.setLogo(picker.getLinks()[0]);
        pg.setInfo(info.getText().toString());
        pg.setNumber(phone.getText().toString());
        pg.setTelegramLink(telegramLink.getText().toString());
        pg.setWebLink(webLink.getText().toString());
        return pg;
    }
}
