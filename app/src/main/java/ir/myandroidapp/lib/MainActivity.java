package ir.myandroidapp.lib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.Ui.Waiter;
import ir.myandroidapp.library.activities.AddItem;
import ir.myandroidapp.library.activities.AddPage;
import ir.myandroidapp.library.backend.BackendComment;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendUser;
import ir.myandroidapp.library.backend.SimpleResponse;
import ir.myandroidapp.library.cards.CardComment;

public class MainActivity extends Activity {

    Core core;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}