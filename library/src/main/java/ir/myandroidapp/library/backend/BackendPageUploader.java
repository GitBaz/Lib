package ir.myandroidapp.library.backend;

import android.app.Activity;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.ProgressView;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 5/3/17.
 */

public class BackendPageUploader {

    BackendPage page;
    BackendData data;

    Core core;
    String table;
    String path;
    Activity activity;

    public BackendPageUploader(Core cre, String tbl,String iconPath,Activity act) {
        core = cre;
        table = tbl;
        data = new BackendData(cre);
        path = iconPath;
        activity = act;
    }

    public BackendPageUploader create(BackendPage p) {
        page = p;
        return this;
    }

    public void upload() {
        final ProgressView pv = new ProgressView(core.context);
        pv.show();

        if (path.equals("")) {
            data.put(table, page, new SimpleResponse() {
                @Override
                public void onSuccess() {
                    pv.cancel();
                    core.toast(core.getString(R.string.page_success));
                    activity.finish();
                }

                @Override
                public void onFailure() {
                    pv.cancel();
                }
            });
        } else {

            data.uploadImage(path, new SimpleResponse() {
                @Override
                public void onSuccess() {
                    data.put(table, page, new SimpleResponse() {
                        @Override
                        public void onSuccess() {
                            pv.cancel();
                            core.toast(core.getString(R.string.page_success));
                            activity.finish();
                        }

                        @Override
                        public void onFailure() {
                            pv.cancel();
                        }
                    });

                }

                @Override
                public void onFailure() {
                    pv.cancel();
                }
            });
        }
    }

}