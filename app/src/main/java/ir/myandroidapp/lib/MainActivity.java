package ir.myandroidapp.lib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.activities.AddItem;
import ir.myandroidapp.library.activities.Page;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.BackendObject;
import ir.myandroidapp.library.backend.BackendPage;
import ir.myandroidapp.library.backend.BackendUser;

public class MainActivity extends AddItem {

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

    @Override
    public void onBackPressed() {
        new BackendData(core).getUserPage(new BackendData.GetUserPage() {
            @Override
            public void onExists(final BackendPage page) {

                new BackendData(core).getUserPagePosts(page.getUser(), new BackendData.GetUserPagePosts() {
                    @Override
                    public void onExists(BackendObject[] objects) {
                        setContentView(new Page(core.context,core,page,objects));

                    }

                    @Override
                    public void onNotExists() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }

            @Override
            public void onNotExists() {
                core.toast("not exists");
            }

            @Override
            public void onFailure() {

            }
        });

    }

}