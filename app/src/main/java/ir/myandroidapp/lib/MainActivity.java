package ir.myandroidapp.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.Remember;
import ir.myandroidapp.library.activities.AddItem;
import ir.myandroidapp.library.activities.AddPage;
import ir.myandroidapp.library.activities.ListView;
import ir.myandroidapp.library.activities.Page;
import ir.myandroidapp.library.activities.PageActivity;
import ir.myandroidapp.library.activities.Profile;
import ir.myandroidapp.library.activities.Searcher;
import ir.myandroidapp.library.activities.SubMain;
import ir.myandroidapp.library.activities.main;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.backend.BackendUser;

public class MainActivity extends PageActivity {

    Core core;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        core = new Core(this);

        Remember.init(this, "nokhche");

        new Primary().init("brand", "https://storage.backtory.com/nokchefile/usersPics/",
                ir.myandroidapp.library.R.color.colorPrimary,
                ir.myandroidapp.library.R.color.colorPrimaryLight,
                ir.myandroidapp.library.R.color.colorPrimaryDark);

        core = new Core(this);

        core.forceRTLIfSupported(getWindow());

        BackendUser user = new BackendUser(core);
        user.init("58f5dd79e4b0f42c8b96715a", "58f5dd79e4b05d0bd4f0a823",
                "58f5dd7ae4b0f42c8b96715d", "58f5de1be4b0f42c8b9671a3");
        user.login("123456", "123456", new BackendUser.Response() {
            @Override
            public void onSuccess() {
                core.toast("work");
            }

            @Override
            public void onFailure() {

            }
        });

    }

    @Override
    public void onBackPressed() {
        setContentView(new Searcher(core.context,core,this));

    }
}