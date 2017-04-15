package ir.myandroidapp.library.backend;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryFile;
import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryResponse;

import java.io.File;

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

    public void put(String name, String pics, String pp, String sp, String info, String details,
                                      String cat, String checked, String place,String user, final SimpleResponse response){
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
        object.put("user",user);
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
}