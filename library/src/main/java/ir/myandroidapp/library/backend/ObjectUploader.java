package ir.myandroidapp.library.backend;

import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryUser;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.ProgressView;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 4/30/17.
 */

public class ObjectUploader {

    Core core;
    BackendObject object;
    BackendData data;
    String[] paths;
    String table = "";

    public ObjectUploader(Core cre, String tbl, String[] path) {
        core = cre;
        object = new BackendObject();
        data = new BackendData(cre);
        table = tbl;
        int count = path.length;
        paths = new String[count];
        for (int i = 0; i < count; i++) {
            paths[i] = "";
        }
        paths = path;
    }

    public ObjectUploader create(String name, String sp, String pp, String info, String[] links, String details) {
        object.setName(name);
        object.setSecondaryPrice(sp);
        object.setPrimaryPrice(pp);
        object.setInfo(info);
        object.setChecked("0");
        object.setPlace("");
        object.setCat("");
        object.setDetails(details);
        object.setPics(core.combineNoNull(links, '|'));

        return this;
    }

    public ObjectUploader create(BackendObject obj) {
        object = obj;
        return this;
    }

    public void upload() {

        final ProgressView pv = new ProgressView(core.context);
        pv.show();

        if (core.removeNullStrings(paths).length == 0) {
            data.put(table, object, new SimpleResponse() {
                @Override
                public void onSuccess() {
                    pv.cancel();
                    core.toast(core.getString(R.string.put_success));
                }

                @Override
                public void onFailure() {
                    pv.cancel();
                }
            });
        } else {

            data.uploadImages(core.removeNullStrings(paths), new SimpleResponse() {
                @Override
                public void onSuccess() {
                    data.put(table, object, new SimpleResponse() {
                        @Override
                        public void onSuccess() {
                            pv.cancel();
                            core.toast(core.getString(R.string.put_success));
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