package ir.myandroidapp.library.backend;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryFile;
import com.backtory.java.internal.BacktoryObject;

import java.io.File;
import java.util.List;

/**
 * Created by kam.amir on 4/8/17.
 */

public class BackendData {

    String table = "";

    public BackendData(String tbl){
        table=tbl;
    }

    public void getById(String id, BacktoryCallBack<BacktoryObject> callBack) {
        BacktoryObject.GetQuery(table).getInBackground(id, callBack);
    }

    public void getMathes(String where,String is,BacktoryCallBack<List<BacktoryObject>> callBack){
        BacktoryObject.GetQuery(table).whereMatches(where,is).findInBackground(callBack);
    }

    public void getContains(String where,String is,BacktoryCallBack<List<BacktoryObject>> callBack){
        BacktoryObject.GetQuery(table).whereContains(where,is).findInBackground(callBack);
    }

    public void getAll(BacktoryCallBack<List<BacktoryObject>> callBack){
        BacktoryObject.GetQuery(table).findInBackground(callBack);
    }

    public void uploadImage(String path){
        new BacktoryFile().beginUpload(new File(path));
    }


}