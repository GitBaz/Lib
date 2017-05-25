package ir.myandroidapp.lib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.Remember;
import ir.myandroidapp.library.activities.AddItem;
import ir.myandroidapp.library.activities.ItemActivity;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendUser;
import ir.myandroidapp.library.cards.CardView;
import ir.myandroidapp.library.cards.CardViewLarge;

public class MainActivity extends AddItem {

    Core core;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        core = new Core(this);

        Remember.init(this,"nokhche");

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
        new BackendData(core).getSearch("گوجه", new BackendData.GetObject() {
            @Override
            public void onSuccess(BackendObject[] obj) {
                setContentView(new CardViewLarge(core.context,core,MainActivity.this,obj[0]));
            }

            @Override
            public void onFailure() {

            }
        });    }
}