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

    public void getById(String id, BacktoryCallBack<BacktoryObject> callBack) {
        BacktoryObject.GetQuery(table).getInBackground(id, callBack);
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
        new BacktoryFile().beginUpload(new File(path)).commitInBackground(new BacktoryCallBack<String>() {
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


    private void writeImageOnDataBase(String name){
        BacktoryObject object = new BacktoryObject(table);
      //  object.put();
        new BacktoryObject(table).put("name","ali");
    }

    public void addItem() {
//        BacktoryQuery.getQuery(table).
    }

    interface Result {
//        void result(String name,String );
    }

}