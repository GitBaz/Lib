package ir.myandroidapp.library.backend;

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

    public BackendPageUploader(Core cre, String tbl,String iconPath) {
        core = cre;
        table = tbl;
        data = new BackendData(cre);
        path = iconPath;
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