package ir.myandroidapp.library.backend;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryFile;
import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.realtime.core.BacktorySender;
import com.backtory.java.realtime.core.ConnectorClient;

import java.io.File;
import java.util.List;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 4/8/17.
 */

public class BackendData {

    String table = "";
    Core core ;

    public BackendData(Core cre, String tbl) {
        table = tbl;
        core = cre;
    }

    public void getById(String id, final BackendItem item) {
        BacktoryObject.GetQuery(table).getInBackground(id, new BacktoryCallBack<BacktoryObject>() {
            @Override
            public void onResponse(BacktoryResponse<BacktoryObject> backtoryResponse) {
                if(backtoryResponse.isSuccessful()){
                    item.item(backtoryResponse.body().getString("name"),
                            backtoryResponse.body().getString("pics"),
                            backtoryResponse.body().getString("pp"),
                            backtoryResponse.body().getString("sp"),
                            backtoryResponse.body().getString("info"),
                            backtoryResponse.body().getString("details"),
                            backtoryResponse.body().getString("cat"),
                            backtoryResponse.body().getString("checked"),
                            backtoryResponse.body().getString("place"));
                }else{
                    item.onFailure();
                    core.toast(core.getString(R.string.connection_error));
                }
            }
        });
    }

    public void getMathes(String where, String is, BacktoryCallBack<List<BacktoryObject>> callBack) {
        BacktoryObject.GetQuery(table).whereMatches(where, is).findInBackground(callBack);
    }

    public void getContains(String where, String is, BacktoryCallBack<List<BacktoryObject>> callBack) {
        BacktoryObject.GetQuery(table).whereContains(where, is).findInBackground(callBack);
    }

    public void getAll(BacktoryCallBack<List<BacktoryObject>> callBack) {
        BacktoryObject.GetQuery(table).findInBackground(callBack);
    }

    public void uploadImage(String path, final SimpleResponse response) {
        new BacktoryFile().beginUpload(new File(path),"usersPics/").commitInBackground(new BacktoryCallBack<String>() {
            @Override
            public void onResponse(BacktoryResponse<String> backtoryResponse) {
                if(backtoryResponse.isSuccessful()){
                    response.onSuccess();
                }else{
                    response.onFailure();
                    core.toast(core.getString(R.string.upload_error));
                }
            }
        });
    }


    public void writeImageOnDataBase(String name, String pics, String pp, String sp, String info, String details,
                                      String cat, String checked, String place, final SimpleResponse response){
        BacktoryObject object = new BacktoryObject(table);
        object.put("name",name);
        object.put("pics",pics);
        object.put("pp",pp);
        object.put("sp",sp);
        object.put("info",info);
        object.put("details",details);
        object.put("cat",cat);
        object.put("checked",checked);
        object.put("place",place);
        object.saveInBackground(new BacktoryCallBack<Void>() {
            @Override
            public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                if(backtoryResponse.isSuccessful()){
                    response.onSuccess();
                }else{
                    response.onFailure();
                    core.toast(core.getString(R.string.upload_error));
                }
            }
        });
    }

    public void addItem() {
//        BacktoryQuery.getQuery(table).
    }

    interface Result {
//        void result(String name,String );
    }

}