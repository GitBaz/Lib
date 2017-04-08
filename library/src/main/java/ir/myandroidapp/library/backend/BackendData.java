package ir.myandroidapp.library.backend;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryObject;

import java.util.List;

/**
 * Created by kam.amir on 4/8/17.
 */

public class BackendData {

    public BackendData(){
    }

    public void getItem(String table, String id, BacktoryCallBack<BacktoryObject> callBack) {
        BacktoryObject.GetQuery(table).getInBackground(id, callBack);
    }

    public void getItems(String table,String where,String is,BacktoryCallBack<List<BacktoryObject>> callBack){
        BacktoryObject.GetQuery(table).whereMatches(where,is).findInBackground(callBack);
    }

    public void search(String table,String key,BacktoryCallBack<List<BacktoryObject>> callBack){
        BacktoryObject.GetQuery(table).whereExists(key).findInBackground(callBack);
    }




}
